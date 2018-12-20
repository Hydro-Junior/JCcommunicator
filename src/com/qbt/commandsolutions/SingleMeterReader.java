package com.qbt.commandsolutions;

import java.net.SocketException;

import com.qbt.entity.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;
import com.qbt.util.GsonUtil;
import com.qbt.util.ReturnGetter;

public class SingleMeterReader extends CommandHandler {

	public SingleMeterReader(Command command) {
		super(command);
	}

	public SingleMeterReader(Command command, int timeOut) {
		super(command, timeOut);
	}

	@Override
	public void run() {
		Logger logger = Logger.getLogger(SingleMeterReader.class);
		switch (this.command.getType()) {
		case 0:
			DBHandler.updateCmd_TP(command, 3);// 更新命令状态为正在执行
			Request req = new Request("READMETER_REALTIME",
					new DataOfReq(command.getContentValue1(), command.getContentValue3()));
			String s = GsonUtil.objToJson(req);
			try {
				String ss = (String) ReturnGetter.getReturn(s);
				JSONObject job = new JSONObject(ss);
				// 如果没有错，将表读数写入数据库，完成命令后更改命令状态。
				if (job.getJSONObject("error").getString("code").equals("0")) {
					DBHandler.updateCmd_TP(command, 5); // 执行成功
					double value = Double.parseDouble(job.getJSONObject("data").getString("zy"));
					String valveStateStr = job.getJSONObject("data").getString("status_door");
					int valveState = 1;
					System.out.println("收到阀门状态："+ valveStateStr);
					if(!valveStateStr.equals("阀开")) {
						valveState = 2;
					}
					int centerId = DBHandler.getCenterId(command.getContentValue1());
					DeviceTmp dtmp = DBHandler.getLastDeviceTmp(centerId, command);
					if(dtmp != null){
						DBHandler.updateDeviceTmp(value, centerId, command);
					}else{
						DBHandler.writeDeviceTmp(value, centerId, command);
					}
					DBHandler.updateStrobeState(valveState,centerId,command.getContentValue3());
					//如果是大表，再往大表读表记录里写
					Device dev = DBHandler.getDevice(centerId,command.getContentValue3());
					if(dev != null && dev.getBigDeviceFlag() != 0){
						DBHandler.writeBigDeviceRecord(value, centerId, command);
					}
					DBHandler.updateCenterAfterRead(centerId); //更新集中器表
				} else {
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
