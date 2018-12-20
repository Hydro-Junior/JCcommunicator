package com.qbt.handlers;

import java.util.ArrayList;
import java.util.List;

import com.qbt.dao.CenterDao;
import com.qbt.dao.CmdDao;
import com.qbt.dao.DeviceDao;
import com.qbt.dao.DeviceTmpDao;
import com.qbt.dao.LargeDeviceDao;
import com.qbt.daoImpl.CenterDaoImpl;
import com.qbt.daoImpl.CmdDaoImpl;
import com.qbt.daoImpl.DeviceDaoImpl;
import com.qbt.daoImpl.DeviceTmpDaoImpl;
import com.qbt.daoImpl.LargeDeviceDaoImpl;
import com.qbt.entity.Center;
import com.qbt.entity.Command;
import com.qbt.entity.Device;
import com.qbt.entity.DeviceTmp;
import com.qbt.entity.LargeCaliberDevice;
import com.qbt.entity.Meter;

public class DBHandler {
	//得到计划命令
	public static List<Command> getPlanCommand(){
		
		CmdDao dao = new CmdDaoImpl();
		List<Command> cmd = new ArrayList<Command>();
		try {
			cmd = dao.queryCommand_PL();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmd==null? new ArrayList<>():cmd;
		
	}
	//得到临时命令
	public static List<Command> getTempCommand(){
		CmdDao dao = new CmdDaoImpl();
		List<Command> cmd = new ArrayList<Command>();
		try {
			cmd = dao.queryCommand_TP();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmd==null? new ArrayList<>():cmd;
	}
	//查询本协议所有集中器
	public static List<Center> findAllCenter(){
		CenterDao dao = new CenterDaoImpl();
		List<Center> center = dao.queryAllCenter();
		return center==null? new ArrayList<>():center;
	}
	//更新集中器状态
	public static boolean updateCenterState(int state, String gprsId){
		CenterDao dao = new CenterDaoImpl();
		return dao.updateCenterStatue(state, gprsId);
	}
	//更新数据库中的心跳时间
	public static boolean updateHeartBeatTime(String gprsId) {
		CenterDao dao = new CenterDaoImpl();
		return dao.updateCenterHeartBeat(gprsId);
	}
	//更新数据库中临时命令的状态
	public static boolean updateCmd_TP(Command c , int s){
		CmdDao a = new CmdDaoImpl();
		return a.updateCommandState_TP(c, s);
	}
	//更新数据库中计划命令的状态
	public static boolean updateCmd_PL(Command c , int s){
		CmdDao a = new CmdDaoImpl();
		return a.updateCommandState_PL(c, s);
	}
	//查找指定集中器号的集中器id
	public static int getCenterId(String gprsNum){
		CenterDao a = new CenterDaoImpl();
		return a.queryCenterId(gprsNum);
	}
	//往deviceTmp表中添加读表记录
	public static boolean writeDeviceTmp(double value,int centerId,Command command){
		DeviceTmpDao a = new DeviceTmpDaoImpl();
		return a.saveDeviceTmp(value, centerId, command);
	}
    public static boolean updateDeviceTmp(double value,int centerId,Command command){
        DeviceTmpDao a = new DeviceTmpDaoImpl();
        return a.updateDeviceTmp(value, centerId, command);
    }
	//往largeCaliberDevice表中添加读表记录
	public static boolean writeBigDeviceRecord(double value ,int centerId,Command command){
		LargeDeviceDao a = new LargeDeviceDaoImpl();
		return a.saveRecord(value, centerId, command);
	}
	//删除DeviceTmp表中某水表当日的读表记录
	public static boolean delDeviceTmp(int centerid,Command command){
		DeviceTmpDao a = new DeviceTmpDaoImpl();
		return a.deleteDeviceTmp(centerid, command);
	}
	
	//得到上一条临时读表记录
	public static DeviceTmp getLastDeviceTmp(int centerid, Command command){
		DeviceTmpDao a = new DeviceTmpDaoImpl();
		return a.queryLastDeviceTmp(centerid, command);
	}
	//删除计划命令
	public static boolean deletePlanCommand(Command c){
		CmdDao a = new CmdDaoImpl();
		return a.delCommand_PL(c);
	}
	//读表后更新设备表的信息
	public static boolean updateDeviceAfterRead(DeviceTmp tmp){
		DeviceDao a = new DeviceDaoImpl();
	    return a.updateDevice(tmp);
	}
	//读表后更新集中器的信息
	public static boolean updateCenterAfterRead(int id){
		CenterDao a = new CenterDaoImpl();
		return a.updateCenterReadTime(id);
	}
	//更新阀门状态信息
	public static boolean updateStrobeState(int state, int centerId, String iAddr){
		DeviceDao a = new DeviceDaoImpl();
		return a.setStrobeState(state, centerId, iAddr);
	}
	//批量更新阀门状态信息
	public static boolean batchSetSrobeState(int state, int centerId){
		DeviceDao a = new DeviceDaoImpl();
		return a.setAllStrobeState(state, centerId);
	}
	//删除某集中器某表下的此前所有计划命令
	public static boolean deletePlanQueueBefore(String gprsNum){
		CmdDao a = new CmdDaoImpl();
		return a.delPlanQueueBefore(gprsNum);
	}
	//查询某集中器下所有表
	public static List<Meter> getMeterList(int centerid){
		DeviceDao a = new DeviceDaoImpl();
		return a.queryAllDevice(centerid);	
		}
	public static Device getDevice(int centerId, String iAddr){
		DeviceDao a = new DeviceDaoImpl();
		return a.queryDevice(centerId, iAddr);
	}
	//读取集中器信息
	public static boolean readCenterInfo(Command c,String scheme){
		CmdDao a = new CmdDaoImpl();
		return a.updateCenterInfo_TP(c, scheme);
	}
}
