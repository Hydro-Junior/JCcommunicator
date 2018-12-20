package com.qbt.daoImpl;

import java.util.ArrayList;
import java.util.List;

import com.qbt.dao.LargeDeviceDao;
import com.qbt.entity.Command;

public class LargeDeviceDaoImpl  extends BaseDao implements LargeDeviceDao {

	@Override
	public boolean saveRecord(double value, int centerId, Command c) {
		String sql = "insert into t_largeCaliberDevice  (addr,readTime,centerID,showValue,"
				+ "meterState,enprNo) values"
				+ " (?,GETDATE(),?,?,0,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(c.getContentValue3());
		params.add(centerId);
		params.add(value);
		params.add(c.getEnprNo());
		return this.operUpdate(sql, params);
	}

}
