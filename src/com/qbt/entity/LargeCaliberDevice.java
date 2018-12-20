package com.qbt.entity;

import java.sql.Timestamp;

public class LargeCaliberDevice {
	private int id ;
	private String addr;
	private Timestamp readTime;
	private int centerID;
	private double showValue;
	private int meterState;
	private String enprNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Timestamp getReadTime() {
		return readTime;
	}
	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}
	public int getCenterID() {
		return centerID;
	}
	public void setCenterID(int centerID) {
		this.centerID = centerID;
	}
	public double getShowValue() {
		return showValue;
	}
	public void setShowValue(double showValue) {
		this.showValue = showValue;
	}
	public int getMeterState() {
		return meterState;
	}
	public void setMeterState(int meterState) {
		this.meterState = meterState;
	}
	public String getEnprNo() {
		return enprNo;
	}
	public void setEnprNo(String enprNo) {
		this.enprNo = enprNo;
	}
	
}
