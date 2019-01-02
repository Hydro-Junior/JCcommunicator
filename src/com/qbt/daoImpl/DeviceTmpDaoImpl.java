package com.qbt.daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.qbt.dao.DeviceTmpDao;
import com.qbt.entity.Command;
import com.qbt.entity.DeviceTmp;



public class DeviceTmpDaoImpl extends BaseDao implements DeviceTmpDao {

	//插入临时读表记录
	private static SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
	
	public boolean saveDeviceTmp(double value,int centerId,Command command){
		String dt = f.format(new Date());
		String tableName = "t_deviceTmp"+dt;
		createTempDeviceTable(tableName);
		String sql = "insert into t_deviceTmp"+dt+" (addr,readDate,centerID,showValue,fshowValue,"
				+ "meterState,commState,isUse,readTime,enprNo) values"
				+ " (?,?,?,?,?,0,0,0,GETDATE(),?)";
		Calendar now = Calendar.getInstance();
		int readDate = now.get(Calendar.DAY_OF_MONTH);
		List<Object> params = new ArrayList<Object>();
		params.add(command.getContentValue3());
		params.add(readDate);
		params.add(centerId);
		params.add(value);
		params.add(value);
		params.add(command.getEnprNo());
		return this.operUpdate(sql, params);
	}

	//更新临时读表记录
	public boolean updateDeviceTmp(double value,int centerId,Command command){
		String dt = f.format(new Date());
		String tableName = "t_deviceTmp"+dt;
		createTempDeviceTable(tableName);
		String sql = "update t_deviceTmp"+dt+" set readTime=GETDATE(),showValue=?,fshowValue=? where centerID=? and readDate=? and addr=?";
		Calendar now = Calendar.getInstance();
		int readDate = now.get(Calendar.DAY_OF_MONTH);
		List<Object> params = new ArrayList<Object>();
		params.add(value);
		params.add(value);
		params.add(centerId);
		params.add(readDate);
		params.add(command.getContentValue3());
		return this.operUpdate(sql, params);
	}

	//删除对应某集中器下某表当日的读取记录
	public boolean deleteDeviceTmp(int centerid,Command command) {

		String dt = f.format(new Date());
		String sql = "delete from t_deviceTmp"+dt+" where addr=? and centerID=? and readDate=?";
		Calendar now = Calendar.getInstance();
		int readDate = now.get(Calendar.DAY_OF_MONTH);
		List<Object> params = new ArrayList<Object>();
		params.add(command.getContentValue3());
		params.add(centerid);
		params.add(readDate);
		return this.operUpdate(sql, params);
	}

	@Override
	public DeviceTmp queryLastDeviceTmp(int centerId, Command command) {

		String dt = f.format(new Date());
		List<DeviceTmp> deviceTmps = new ArrayList<DeviceTmp>();
		Calendar now = Calendar.getInstance();
		int readDate = now.get(Calendar.DAY_OF_MONTH);
		String sql = "select * from t_deviceTmp"+dt+" where addr=? and centerID=? and "
				+ " readDate=?";
		List<Object> params = new ArrayList<Object>();
		params.add(command.getContentValue3());
		params.add(centerId);
		params.add(readDate);
		try {
			deviceTmps = this.operQuery(sql, params, DeviceTmp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!deviceTmps.isEmpty()){
			return deviceTmps.get(0);
		}else{
			return null;
		}
	}
	public void createTempDeviceTable(String tableName) {
		if (this.checkTableExists(tableName)) return;
		String sql = "CREATE TABLE " + tableName + " " +
				"(addr varchar(20) not NULL, " +
				"readDate int not NULL, " +
				"centerID int not NULL, " +
				"showValue numeric(19,4)," +
				"fshowValue numeric(19,4)," +
				"meterState int, " +
				"commState int," +
				"isUse int," +
				"readTime datetime," +
				"enprNo varchar(20)," +
				"PRIMARY KEY (addr,readDate,centerID))";
		this.operUpdate(sql, null);
	}
}

