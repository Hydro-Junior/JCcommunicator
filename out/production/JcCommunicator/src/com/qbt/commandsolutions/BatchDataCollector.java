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

public class BatchDataCollector extends CommandHandler {

	public BatchDataCollector(Command command) {
		super(command);
	}

	public BatchDataCollector(Command command, int timeOut) {
		super(command, timeOut);
	}
	@Override
	public void run() {
		Logger logger = Logger.getLogger(BatchDataCollector.class);
		switch (this.command.getType()) {
		case 0:
			DBHandler.updateCmd_TP(command, 3);
			Request req = new Request("READMETER_BATCH", new DataOfReq(command.getContentValue1()));
			String s = GsonUtil.objToJson(req);
			try {
				System.out.println("发送采集指令！");
				String ss = (String) ReturnGetter.getReturn(s);
				JSONObject job = new JSONObject(ss);
				if (job.getJSONObject("error").getString("code").equals("0")) {
					DBHandler.updateCmd_TP(command, 5);
				} else {
					DBHandler.updateCmd_TP(command, 6);
				}
				logger.info(ss);
			} catch (Exception e) {
				DBHandler.updateCmd_TP(command, 6);
				e.printStackTrace();
				logger.error(e.toString());
			}
			break;
		case 1:
			DBHandler.updateCmd_PL(command, 3);
			Request req2 = new Request("READMETER_BATCH", new DataOfReq(command.getContentValue1()));
			String s2 = GsonUtil.objToJson(req2);
			try {
				String ss = (String) ReturnGetter.getReturn(s2);
				JSONObject job = new JSONObject(ss);
				if (job.getJSONObject("error").getString("code").equals("0")) {
					DBHandler.updateCmd_PL(command, 5);
					DBHandler.deletePlanCommand(command);

				} else {
					DBHandler.updateCmd_PL(command, 6);
				}
				logger.info(ss);
			
			} catch (Exception e) {
				DBHandler.updateCmd_PL(command, 6);
				e.printStackTrace();
				logger.error(e.toString());
			}
			break;
		default:
			break;
		}
	}

}
