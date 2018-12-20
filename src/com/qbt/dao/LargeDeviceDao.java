package com.qbt.dao;

import com.qbt.entity.Command;

public interface LargeDeviceDao {
	public boolean saveRecord(double value , int centerId, Command c);
}
