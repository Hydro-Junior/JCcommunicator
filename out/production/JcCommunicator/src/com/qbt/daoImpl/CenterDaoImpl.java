package com.qbt.daoImpl;

import java.util.ArrayList;
import java.util.List;

import com.qbt.dao.CenterDao;
import com.qbt.entity.Center;
import com.qbt.entity.DeviceTmp;

public class CenterDaoImpl extends BaseDao implements CenterDao {

	@Override
	/**
	 * 查询在线的本协议集中器（已测试）
	 */
	public List<Center> queryOnlineCenter() {
		List<Center> centers = null;
		String sql = "select id,name,protocolType,gprsNum,readTime,runStatue,isUse,uplinkType"
				+ ",enprNo,readSchemeId,isAutoCollection,downloadFlag,portId,areaId from t_center"
				+ " where runStatue = 1 and protocolType = 4";
		try {
			centers = this.operQuery(sql, null, Center.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return centers;
	}

	/**
	 * 查询所有本协议集中器（已测试）
	 */
	public List<Center> queryAllCenter() {
		List<Center> centers = null;
		String sql = "select id,name,protocolType,gprsNum,readTime,runStatue,isUse,uplinkType"
				+ ",enprNo,readSchemeId,isAutoCollection,downloadFlag,portId,areaId from t_center"
				+ " where protocolType = 4";
		try {
			centers = this.operQuery(sql, null, Center.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return centers;
	}

	@Override
	/**
	 * 集中器状态更新（已测试）
	 */
	public boolean updateCenterStatue(int state,String gprsId) {
		List<Center> centers = null;
		String sql = "update t_center set runStatue=? where gprsNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(state);
		params.add(gprsId);
		return this.operUpdate(sql, params);
	}
	

	@Override
	public int queryCenterId(String gprsNum) {
		List<Center> center = null;
		String sql = "select id from t_center where gprsNum = ?" ;
		List<Object> params = new ArrayList<Object>();
		params.add(gprsNum);
		try {
			center = this.operQuery(sql, params, Center.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return center.get(0).getId();
	}

	@Override
	public boolean updateCenterReadTime(int centerId) {
		String sql = "update t_center set readTime=GETDATE() where id=?";
		List<Object> params = new ArrayList<Object>();
		params.add(centerId);
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean updateCenterHeartBeat(String gprsId) {
		String sql = "update t_center set heartBeatTime = GETDATE() where gprsNum = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(gprsId);
		return this.operUpdate(sql, params);
	}

}
