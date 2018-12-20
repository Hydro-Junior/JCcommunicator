package com.qbt.dao;

import com.qbt.entity.Command;
import com.qbt.entity.DeviceTmp;

public interface DeviceTmpDao {
	//插入临时读表记录
	public boolean saveDeviceTmp(double value,int centerId,Command command);
	//删除对应某集中器下某表当日的读取记录
	public boolean deleteDeviceTmp(int centerid,Command command);
	//更新临时读表记录
	public boolean updateDeviceTmp(double value,int centerId,Command command);
	//查询某集中器某表最新的一条读表记录
	public DeviceTmp queryLastDeviceTmp(int centerid,Command command);
}
