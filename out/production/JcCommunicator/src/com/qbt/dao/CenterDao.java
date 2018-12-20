package com.qbt.dao;

import java.util.List;

import com.qbt.entity.Center;
import com.qbt.entity.DeviceTmp;

public interface CenterDao {
	//查询在线的集中器
	public List<Center> queryOnlineCenter();
	//查询本协议的所有集中器
	public List<Center> queryAllCenter();
	//更改集中器的在线状态
	public boolean updateCenterStatue(int state,String gprsId);
	//查找对应集中器号的集中器id（在命令处理时有用）
	public int queryCenterId(String gprsNum);
	//更新集中器的读表时间
	public boolean updateCenterReadTime(int CenterId);
	//更新集中器心跳时间
	public boolean updateCenterHeartBeat(String gprsId);
}
