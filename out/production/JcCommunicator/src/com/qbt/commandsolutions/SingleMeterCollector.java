package com.qbt.commandsolutions;

import java.net.SocketException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.qbt.entity.Command;
import com.qbt.entity.DataOfReq;
import com.qbt.entity.Request;
import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;

import com.qbt.util.GsonUtil;
import com.qbt.util.ReturnGetter;


public class SingleMeterCollector  extends CommandHandler{

	public SingleMeterCollector(Command command) {
		super(command);
	}

	@Override
	public void run() {
		Logger logger = Logger.getLogger(SingleMeterCollector.class);
		switch(this.command.getType()){
		case 0:
			DBHandler.updateCmd_TP(command, 3);//更新命令状态为正在执行
			Request req = new Request("READMETER_REALTIME",new DataOfReq(command.getContentValue1(),command.getContentValue3()));
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
			} catch (SocketException e) {
				DBHandler.updateCmd_TP(command, 1);
			} catch (Exception e) {
				DBHandler.updateCmd_TP(command, 6);
				e.printStackTrace();
				logger.error(e.toString());
				
			}
			break;
		case 1:
			break;
		default:
			break;
	}
	
	}

}