package com.qbt.entity;

import java.util.List;

import com.qbt.entity.Meter;

public class DataOfReq {
	//公司标识
		private String companyflag;
		//集中器号
		private String jzq_ecuid;
		//表号
		private String meter_ecuid;
		//旧表号
		private String oldmeter_ecuid;
		//新表号
		private String newmeter_ecuid;
		//表号之间以逗号分隔
		private List<Meter> meter_list;
		
		//添加表时用到的字段
		private String addrbzw;  //表具安装的小区名称
		private String dz;       //楼栋
		private String dy;		 //单元
		private String lc;       //楼层
		private String hh;		//房号
		private String addrother; //其他信息
		
		
		public String getCompanyflag() {
			return companyflag;
		}
		public void setCompanyflag(String companyflag) {
			this.companyflag = companyflag;
		}
		public String getJzq_ecuid() {
			return jzq_ecuid;
		}
		public void setJzq_ecuid(String jzq_ecuid) {
			this.jzq_ecuid = jzq_ecuid;
		}
		public String getMeter_ecuid() {
			return meter_ecuid;
		}
		public void setMeter_ecuid(String meter_ecuid) {
			this.meter_ecuid = meter_ecuid;
		}
		public String getOldmeter_ecuid() {
			return oldmeter_ecuid;
		}
		public void setOldmeter_ecuid(String oldmeter_ecuid) {
			this.oldmeter_ecuid = oldmeter_ecuid;
		}
		public String getNewmeter_ecuid() {
			return newmeter_ecuid;
		}
		public void setNewmeter_ecuid(String newmeter_ecuid) {
			this.newmeter_ecuid = newmeter_ecuid;
		}
	
		public String getAddrbzw() {
			return addrbzw;
		}
		public void setAddrbzw(String addrbzw) {
			this.addrbzw = addrbzw;
		}
		public String getDz() {
			return dz;
		}
		public void setDz(String dz) {
			this.dz = dz;
		}
		public String getDy() {
			return dy;
		}
		public void setDy(String dy) {
			this.dy = dy;
		}
		public String getLc() {
			return lc;
		}
		public void setLc(String lc) {
			this.lc = lc;
		}
		public String getHh() {
			return hh;
		}
		public void setHh(String hh) {
			this.hh = hh;
		}
		public String getAddrother() {
			return addrother;
		}
		public void setAddrother(String addrother) {
			this.addrother = addrother;
		}
		
		
		
		public List<Meter> getMeter_list() {
			return meter_list;
		}
		public void setMeter_list(List<Meter> meter_list) {
			this.meter_list = meter_list;
		}
		public DataOfReq(String jzq_ecuid){
			this.companyflag = "0001";
			this.jzq_ecuid = jzq_ecuid;
		}
		public DataOfReq(String jzq_ecuid , String meter_ecuid){
			this(jzq_ecuid);
			this.meter_ecuid = meter_ecuid;
		}
		public DataOfReq(String jzq_ecuid , String oldMeter, String newMeter){
			this(jzq_ecuid);
			this.oldmeter_ecuid = oldMeter;
			this.newmeter_ecuid = newMeter;
		}
		public DataOfReq(String jzq_ecuid,List<Meter> meters){
			this(jzq_ecuid);
			this.meter_list = meters;
		}
		
	
}

