package com.qbt.daoImpl;

import java.util.ArrayList;
import java.util.List;

import com.qbt.dao.CenterDao;
import com.qbt.dao.CmdDao;
import com.qbt.entity.Center;
import com.qbt.entity.Command;
import com.qbt.entity.Constants;
import com.qbt.util.TimeUtil;


public class CmdDaoImpl extends BaseDao implements CmdDao {

	@Override
	/**
	 * 查询临时表命令(已测)
	 */
	public List<Command> queryCommand_TP() {
		List<Command> tempCommand = new ArrayList<>();
		String sql = "select id,command,Operator,GenerateTime,ExecuteTime,port,State,enprNo,"
				+ "ContentValue1, ContentValue2, ContentValue3,ContentValue4,ContentValue5,ContentValue6 "
				+ "from t_tempCommandQueue  where ContentValue1=? and State=0";
		List<Object> params = new ArrayList<Object>();

        CenterDao c = new CenterDaoImpl();
        List<Center> onlineCenters = c.queryOnlineCenter();
        List<String> centerIds = new ArrayList<String>();
        for(int i = 0; i < onlineCenters.size(); i++){
            centerIds.add(onlineCenters.get(i).getGprsNum());
        }
        for(int i = 0; i < centerIds.size(); i++){
            List<Command> cmd = null;
            params.add(centerIds.get(i));
            try {
                cmd = this.operQuery(sql, params, Command.class);
                tempCommand.addAll(cmd);
                params.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		for(Command cm : tempCommand){
			cm.setType(0);
		}
		return tempCommand;
	}

	@Override
	/**
	 * 查询计划表命令
	 */
	public List<Command> queryCommand_PL() {
		
		List<Command> planCommand = new ArrayList<Command>();
		String sql = "select id,command,Operator,GenerateTime,ExecuteTime,planExecuteTime,State,"
				+ "enprNo,ContentValue1,ContentValue2,ContentValue3,ContentValue4,ContentValue5,"
				+ "ContentValue6 from t_planCommandQueue where ContentValue1=? and State=0 and "
				+ "planExecuteTime < GETDATE() and planExecuteTime>=CONVERT(VARCHAR(10),GETDATE(),120)";
		List<Object> params = new ArrayList<Object>();
		CenterDao c = new CenterDaoImpl();
		List<Center> onlineCenters = c.queryOnlineCenter();
		List<String> centerIds = new ArrayList<String>();
		for(int i = 0; i < onlineCenters.size(); i++){
			centerIds.add(onlineCenters.get(i).getGprsNum());
		}
		for(int i = 0; i < centerIds.size(); i++){
			List<Command> cmd = null;
			params.add(centerIds.get(i));
			try {
				cmd = this.operQuery(sql, params, Command.class);
				planCommand.addAll(cmd);
				params.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(Command cm : planCommand){
			cm.setType(1);
		}
		return planCommand;
	}

	@Override
	public boolean updateCommandState_PL(Command c, int s) {
		String sql = "update t_planCommandQueue set State=? where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(s);
		params.add(c.getId());
		return this.operUpdate(sql, params);
	}
	public boolean updateCommandState_TP(Command c, int s) {
		String sql = "update t_tempCommandQueue set State=? where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(s);
		params.add(c.getId());
		return this.operUpdate(sql, params);
	}
	public boolean updateCenterInfo_TP(Command c,String scheme){
		String sql = "update t_tempCommandQueue set ContentValue2 = ?,ContentValue3=?,ContentValue4=?"
				+ " where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(Constants.IP + Constants.PORT);
		params.add(scheme);
		String nowTime = TimeUtil.getNowTime_Chinese();
		params.add(nowTime);
		params.add(c.getId());
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean delCommand_PL(Command c) {
		String sql = "delete from t_planCommandQueue where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(c.getId());
		return this.operUpdate(sql, params);
	}
	public boolean delCommand_TP(Command c) {
		String sql = "delete from t_tempCommandQueue where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(c.getId());
		return this.operUpdate(sql, params);
	}
	//批量删除某集中器此时刻前的所有计划命令
	public boolean delPlanQueueBefore(String gprs){
		String sql = "delete from t_planCommandQueue where ContentValue1 = ? and planExecuteTime < GETDATE() "
				+ "and State = 5";
		List<Object> params = new ArrayList<Object>();
		params.add(gprs);
		return this.operUpdate(sql, params);	
	}
}
