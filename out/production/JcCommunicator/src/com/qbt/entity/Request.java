package com.qbt.entity;

import com.qbt.entity.DataOfReq;

public class Request {
	private String optype;
	private DataOfReq data;
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public DataOfReq getData() {
		return data;
	}
	public void setData(DataOfReq data) {
		this.data = data;
	}
	
	public Request(String op){
		this.optype = op;
	}
	public Request(String op, DataOfReq data){
		this(op);
		this.data = data;
	}

}