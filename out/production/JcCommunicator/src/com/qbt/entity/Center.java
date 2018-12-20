package com.qbt.entity;

import java.sql.Timestamp;

public class Center {
	private int id; //集中器id
	private String name; //设备名称
	private int runStatue; //当前状态 0不在线 1在线
	private int isUse; //是否使用，0未使用，1使用
	private int protocolType; //协议类型，4表示精诚协议
	private String gprsNum; //集中器号
	private Timestamp readTime; //最后一次读取时间
	private Integer uplinkType; // 集中器上行连接类型
	private String enprNo;//水司
	private Integer readSchemeId;//抄表方案，默认为1，关联第一条，第一条初步定为每天抄一次，时间为晚上12点
	private Integer isAutoCollection;//是否支持自动采集 0表示不支持，1表示支持
	private String installAddr;//集中器安装地址
	private Integer downloadFlag;//导入的集中器下载用户请况 -1表示手动添加未下载用户档案 0表示excel导入资料未下载 1表示已下载用户档案

	private int readPeriod;//读取周期(秒)
	private int readType;//读取类型 0主动读取，1自动上报
	private String config;//配置信息
	private String remark;//备注
	private String modem;//
	private Integer portId;//端口id
	private int areaId;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRunStatue() {
		return runStatue;
	}
	public void setRunStatue(int runStatue) {
		this.runStatue = runStatue;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	public int getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}
	public String getGprsNum() {
		return gprsNum;
	}
	public void setGprsNum(String gprsNum) {
		this.gprsNum = gprsNum;
	}
	public Timestamp getReadTime() {
		return readTime;
	}
	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}
	public int getReadPeriod() {
		return readPeriod;
	}
	public void setReadPeriod(int readPeriod) {
		this.readPeriod = readPeriod;
	}
	public int getReadType() {
		return readType;
	}
	public void setReadType(int readType) {
		this.readType = readType;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModem() {
		return modem;
	}
	public void setModem(String modem) {
		this.modem = modem;
	}

	public String getEnprNo() {
		return enprNo;
	}
	public void setEnprNo(String enprNo) {
		this.enprNo = enprNo;
	}

	public int getUplinkType() {
		return uplinkType;
	}
	public void setUplinkType(int uplinkType) {
		this.uplinkType = uplinkType;
	}
	public int getReadSchemeId() {
		return readSchemeId;
	}
	public void setReadSchemeId(int readSchemeId) {
		this.readSchemeId = readSchemeId;
	}
	public int getIsAutoCollection() {
		return isAutoCollection;
	}
	public void setIsAutoCollection(int isAutoCollection) {
		this.isAutoCollection = isAutoCollection;
	}
	public String getInstallAddr() {
		return installAddr;
	}
	public void setInstallAddr(String installAddr) {
		this.installAddr = installAddr;
	}
	public int getDownloadFlag() {
		return downloadFlag;
	}
	public void setDownloadFlag(int downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	public int getPortId() {
		return portId;
	}
	public void setPortId(int portId) {
		this.portId = portId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	
}
