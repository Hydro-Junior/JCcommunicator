package com.qbt.dao;

import java.util.List;

import com.qbt.entity.Device;
import com.qbt.entity.DeviceTmp;
import com.qbt.entity.Meter;

public interface DeviceDao {
	//更改某集中器下水表所有阀门状态
	public boolean setAllStrobeState(int state,int centerId);
	//更改某集中器某表的阀门状态
	public boolean setStrobeState(int state,int centerId,String iAddr);
	//查询某集中器所有表
	public List<Meter> queryAllDevice(int centerId);
	//查询某集中器特定表
	public Device queryDevice(int centerId,String iAddr);
	//读表后更新表
	public boolean updateDevice(DeviceTmp tmp);
}
