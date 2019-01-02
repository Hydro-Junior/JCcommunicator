package com.qbt.commandsolutions;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.qbt.entity.Command;
import com.qbt.entity.DataOfReq;
import com.qbt.entity.Request;
import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;
import com.qbt.util.GsonUtil;
import com.qbt.util.ReturnGetter;



public class BatchDoorOpener extends CommandHandler {

	public BatchDoorOpener(Command command) {
		super(command);
	}
	public BatchDoorOpener(Command command, int timeOut) {
		super(command,timeOut);
	}

	@Override
	public void run() {
		Logger logger = Logger.getLogger(BatchDoorOpener.class);
		switch(this.command.getType()){
		case 0:
			DBHandler.updateCmd_TP(command, 3);
			Request req = new Request("METER_BATCHOPENDOOR",new DataOfReq(command.getContentValue1()));
			String s = GsonUtil.objToJson(req);
			try {
				String ss = (String)ReturnGetter.getReturn(s);
				JSONObject job = new JSONObject(ss);
				//如果返回值没有错，设置阀门状态为开，完成命令后更改命令状态。
				if(job.getJSONObject("error").getString("code").equals("0")){
					DBHandler.updateCmd_TP(command, 5); //执行成功
					int centerId = DBHandler.getCenterId(command.getContentValue1()); 
					DBHandler.batchSetSrobeState(1, centerId);
					
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
			DBHandler.updateCmd_PL(command, 3);
			Request req2 = new Request("METER_BATCHOPENDOOR",new DataOfReq(command.getContentValue1()));
			String s2 = GsonUtil.objToJson(req2);
			try {
				String ss2 = (String)ReturnGetter.getReturn(s2);
				JSONObject job = new JSONObject(ss2);
				//如果返回值没有错，设置阀门状态为开，完成命令后更改命令状态。
				if(job.getJSONObject("error").getString("code").equals("0")){
					DBHandler.updateCmd_PL(command, 5); //执行成功
					int centerId = DBHandler.getCenterId(command.getContentValue1()); 
					DBHandler.batchSetSrobeState(1, centerId);
					DBHandler.deletePlanQueueBefore(command.getContentValue1());
					
				}else{
					DBHandler.updateCmd_PL(command, 6);
				}
				logger.info(ss2);
			} catch (Exception e) {
				DBHandler.updateCmd_PL(command, 6);
				e.printStackTrace();
				logger.error(e.toString());
			}
		default:break;
		}
	}

}