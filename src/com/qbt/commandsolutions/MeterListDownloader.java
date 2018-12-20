package com.qbt.commandsolutions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.qbt.entity.Command;
import com.qbt.entity.DataOfReq;
import com.qbt.entity.Meter;
import com.qbt.entity.Request;
import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;
import com.qbt.util.GsonUtil;
import com.qbt.util.ReturnGetter;



public class MeterListDownloader extends CommandHandler {

	public MeterListDownloader(Command command) {
		super(command);
	}
	public MeterListDownloader(Command command,int timeOut) {
		super(command,timeOut);
	}
	@Override
	public void run() {
		Logger logger = Logger.getLogger(MeterListDownloader.class);
		switch(this.command.getType()){
		case 0:
			DBHandler.updateCmd_TP(command, 3);
			int centerId = DBHandler.getCenterId(command.getContentValue1());
			List<Meter> meters = new ArrayList();
			meters = DBHandler.getMeterList(centerId);
			Request req = new Request("METER_DOWNLIST",new DataOfReq(command.getContentValue1(),meters));
			String s = GsonUtil.objToJson(req);
			try {
				String ss = (String)ReturnGetter.getReturn(s);
				JSONObject job = new JSONObject(ss);
				//如果没有错，将表读数写入数据库，完成命令后更改命令状态。
				if(job.getJSONObject("error").getString("code").equals("0")){
					DBHandler.updateCmd_TP(command, 5); //执行成功
					
				}else{
					DBHandler.updateCmd_TP(command, 6);
				}
				logger.info(ss);
			} catch (Exception e) {
				DBHandler.updateCmd_TP(command, 6);
				e.printStackTrace();
				logger.error(e.toString());
			
			}
		case 1:
		default:break;
		}
	}

}