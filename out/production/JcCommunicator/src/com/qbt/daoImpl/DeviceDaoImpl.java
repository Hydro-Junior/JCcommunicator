package com.qbt.daoImpl;

import java.util.ArrayList;
import java.util.List;

import com.qbt.dao.DeviceDao;
import com.qbt.entity.Device;
import com.qbt.entity.DeviceTmp;
import com.qbt.entity.Meter;



public class DeviceDaoImpl extends BaseDao implements DeviceDao {

	//更改某集中器下水表所有阀门状态
	public boolean setAllStrobeState(int state, int centerId) {
		String sql="update t_device set StrobeStatue=? where "
				+ "id in (select t_device.id from t_device ,t_deviceRelation where "
				+ "t_device.id = t_deviceRelation.deviceId and centerId = ?)";
		List<Object> params = new ArrayList<Object>();
		params.add(state);
		params.add(centerId);
		return this.operUpdate(sql, params);
	}

	//更改某集中器某表的阀门状态
	public boolean setStrobeState(int state, int centerId, String iAddr) {
		String sql = "update t_device set StrobeStatue=? where iAddr=? and "
				+ "id in (select t_device.id from t_device ,t_deviceRelation where "
				+ "t_device.id = t_deviceRelation.deviceId and centerId = ?)";
		List<Object> params = new ArrayList<Object>();
		params.add(state);
		params.add(iAddr);
		params.add(centerId);
		return this.operUpdate(sql, params);
	}

	//查询某集中器所有表(这里的meter是后建立的实体类，字段比device少，节省空间)
	public List<Meter> queryAllDevice(int centerId) {
		String sql = "select t_device.iAddr as meter_ecuid, t_device.userAddr as addrother from "
				+ "t_device ,t_deviceRelation where t_device.id = t_deviceRelation.deviceId and centerId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(centerId);
		List<Meter> meters = null;
		try {
			meters = this.operQuery(sql, params, Meter.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meters;
	}

	@Override
	public Device queryDevice(int centerId, String iAddr) {
		String sql = "select t_device.BigDeviceFlag from t_device ,t_deviceRelation where t_device.id = t_deviceRelation.deviceId and centerId = ?"
				+ " and iAddr = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(centerId);
		params.add(iAddr);
		List<Device> device = null;
		try {
			device = this.operQuery(sql, params, Device.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (device != null && device.size() > 0 ) ? device.get(0) : null;
		
	}

	//读表后更新表(已测试)
	public boolean updateDevice(DeviceTmp tmp) {
		String sql = "update t_device set PrShowValue=?,PrCeDate=? where iAddr=? and "
				+ "id in (select t_device.id from t_device ,t_deviceRelation where "
				+ "t_device.id = t_deviceRelation.deviceId and centerId = ?)";
		List<Object> params = new ArrayList<Object>();
		params.add(tmp.getShowValue());
		params.add(tmp.getReadTime());
		params.add(tmp.getAddr());
		params.add(tmp.getCenterID());
		return this.operUpdate(sql, params);
	}

	

}
