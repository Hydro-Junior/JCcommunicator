package com.qbt.entity;

import java.sql.Timestamp;

public class Command {
	private int id;
	private String command;
	private String Operator;
	private Timestamp GenerateTime;
	private Integer ExecuteTime;
	private String port;
	//0初始状态表示未执行，1表示集中器未连接，2表示执行完成等待执行，3正在执行，4执行错误，重试中，5执行成功，6执行失败
	private int State; 
	private int type;
	private String enprNo;
	private Timestamp planExecuteTime;



	public Timestamp getPlanExecuteTime() {
		return planExecuteTime;
	}

	public void setPlanExecuteTime(Timestamp planExecuteTime) {
		this.planExecuteTime = planExecuteTime;
	}

	private String ContentValue1;
	private String ContentValue2;
	private String ContentValue3;
	private String ContentValue4;
	private String ContentValue5;
	private String ContentValue6;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public Timestamp getGenerateTime() {
		return GenerateTime;
	}

	public void setGenerateTime(Timestamp generateTime) {
		GenerateTime = generateTime;
	}

	public Integer getExecuteTime() {
		return ExecuteTime;
	}

	public void setExecuteTime(Integer executeTime) {
		ExecuteTime = executeTime;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getEnprNo() {
		return enprNo;
	}

	public void setEnprNo(String enprNo) {
		this.enprNo = enprNo;
	}

	public String getContentValue1() {
		return ContentValue1;
	}

	public void setContentValue1(String contentValue1) {
		ContentValue1 = contentValue1;
	}

	public String getContentValue2() {
		return ContentValue2;
	}

	public void setContentValue2(String contentValue2) {
		ContentValue2 = contentValue2;
	}

	public String getContentValue3() {
		return ContentValue3;
	}

	public void setContentValue3(String contentValue3) {
		ContentValue3 = contentValue3;
	}

	public String getContentValue4() {
		return ContentValue4;
	}

	public void setContentValue4(String contentValue4) {
		ContentValue4 = contentValue4;
	}

	public String getContentValue5() {
		return ContentValue5;
	}

	public void setContentValue5(String contentValue5) {
		ContentValue5 = contentValue5;
	}

	public String getContentValue6() {
		return ContentValue6;
	}

	public void setContentValue6(String contentValue6) {
		ContentValue6 = contentValue6;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}

