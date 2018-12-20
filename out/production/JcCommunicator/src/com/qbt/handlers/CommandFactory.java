package com.qbt.handlers;

import com.qbt.commandsolutions.BatchDataCollector;
import com.qbt.commandsolutions.BatchMeterReader;
import com.qbt.commandsolutions.CenterInfoReader;
import com.qbt.commandsolutions.MeterListDownloader;
import com.qbt.commandsolutions.SingleDoorCloser;
import com.qbt.commandsolutions.SingleDoorOpener;
import com.qbt.commandsolutions.SingleMeterCollector;
import com.qbt.commandsolutions.SingleMeterReader;
import com.qbt.commandsolutions.TimeChecker;
import com.qbt.entity.Command;
import com.qbt.handlers.CommandHandler;

public class CommandFactory {
	 public static CommandHandler create(Command c){
		  /**
		   * 按照命令类型完成命令的生产
		   */
	        switch (c.getCommand()){
	            case "000106": //单个水表读数
	                return new SingleMeterReader(c);
	            case "000302": //单个表关阀
	            	return new SingleDoorOpener(c);
	            case "000304": //单个表开阀
	            	return new SingleDoorCloser(c);
	            case "000102": //批量读表
	            	return new BatchMeterReader(c);
	            case "000101": //集中器采集数据
	            	return new BatchDataCollector(c);
	            case "000105": //采集单个水表读数（实际上与单个水表读数相同，只是不改DeviceTmp表）
	            	return new SingleMeterCollector(c);
	            case "000208": //下载用户档案
	            	return new MeterListDownloader(c);
	            case "000103": //读取集中器信息
	            	return new CenterInfoReader(c);
	            case "000204": //设备校时
	                return new TimeChecker(c);
	            default:
	            	return new TimeChecker(c);
	        }
	    }
}