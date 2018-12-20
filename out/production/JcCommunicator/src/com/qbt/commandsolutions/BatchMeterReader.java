package com.qbt.commandsolutions;

import java.net.SocketException;

import com.qbt.entity.*;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;
import com.qbt.util.GsonUtil;
import com.qbt.util.ReturnGetter;

public class BatchMeterReader extends CommandHandler {

	public BatchMeterReader(Command command) {
		super(command);
	}

	public BatchMeterReader(Command command, int timeOut) {
		super(command, timeOut);
	}

	@Override
	public void run() {
		Logger logger = Logger.getLogger(BatchMeterReader.class);
		switch (this.command.getType()) {
		case 0:
			DBHandler.updateCmd_TP(command, 3);
			Request req = new Request("METER_BATCHREADMETERDATA", new DataOfReq(command.getContentValue1()));
			String s = GsonUtil.objToJson(req);
			try {
				String ss = (String) ReturnGetter.getReturn(s);
				JSONObject job = new JSONObject(ss);
				// 如果没有错，将表读数写入数据库，完成命令后更改命令状态。
				if (job.getJSONObject("error").getString("code").equals("0")) {
					DBHandler.updateCmd_TP(command, 5); // 执行成功
					int centerId = DBHandler.getCenterId(command.getContentValue1());
					@SuppressWarnings("unchecked")
					JSONArray meters = job.getJSONArray("data");
					DeviceTmp dtmp = new DeviceTmp();

					for (int i = 0; i < meters.length(); i++) {
						JSONObject j = meters.getJSONObject(i);
						double value = Double.parseDouble(j.getString("zy"));
						command.setContentValue3(j.getString("meter_ecuid"));
						int valveState = j.getString("status_door").equals("阀开")?1:2;
						dtmp = DBHandler.getLastDeviceTmp(centerId, command);
						if(dtmp != null){
							DBHandler.updateDeviceTmp(value, centerId, command);
							//DBHandler.updateDeviceAfterRead(dtmp); // 更新设备表
						}else{
							DBHandler.writeDeviceTmp(value, centerId, command);
							//DBHandler.updateDeviceAfterRead(dtmp); // 更新设备表
						}
						DBHandler.updateStrobeState(valveState,centerId,command.getContentValue3());
						//如果是大表，再往大表读表记录里写
						Device device = DBHandler.getDevice(centerId,command.getContentValue3());
						if(device != null && device.getBigDeviceFlag() != 0){
							DBHandler.writeBigDeviceRecord(value, centerId, command);
						}else if(device == null) {
							logger.error("数据库中查询不到此表信息，请检查是否已经导入表具资料到数据库！\r\n"+
							"表地址： "+command.getContentValue3()+"		集中器ID： "+centerId
							);
						}
					}
					DBHandler.updateCenterAfterRead(centerId);// 更新集中器的读表记录
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
			DBHandler.updateCmd_PL(command, 3);
			Request req2 = new Request("METER_BATCHREADMETERDATA", new DataOfReq(command.getContentValue1()));
			String s2 = GsonUtil.objToJson(req2);
			try {
				String ss2 = (String) ReturnGetter.getReturn(s2);
				JSONObject job = new JSONObject(ss2);
				// 如果没有错，将表读数写入数据库，完成命令后更改命令状态。
				if (job.getJSONObject("error").getString("code").equals("0")) {
					DBHandler.updateCmd_PL(command, 5); // 执行成功
					DBHandler.deletePlanCommand(command); // 删除该计划命令
					int centerId = DBHandler.getCenterId(command.getContentValue1());
					@SuppressWarnings("unchecked")
					JSONArray meters = job.getJSONArray("data");
					DeviceTmp dtmp = new DeviceTmp();

					for (int i = 0; i < meters.length(); i++) {
						JSONObject j = meters.getJSONObject(i);
						double value = Double.parseDouble(j.getString("zy"));
						command.setContentValue3(j.getString("meter_ecuid"));
						if((dtmp=DBHandler.getLastDeviceTmp(centerId, command))!=null){
							if (DBHandler.delDeviceTmp(centerId, command)) { // 删除之前的当日读表记录
								DBHandler.writeDeviceTmp(value, centerId, command);
							} else {
								DBHandler.writeDeviceTmp(value, centerId, command);
							}// 写入新的读表记录
							DBHandler.updateDeviceAfterRead(dtmp); // 更新设备表
						}else{
							DBHandler.writeDeviceTmp(value, centerId, command);
							// 写入新的读表记录
							dtmp = DBHandler.getLastDeviceTmp(centerId, command);
							DBHandler.updateDeviceAfterRead(dtmp); // 更新设备表
						}
						//如果是大表，再往大表读表记录里写
						if(DBHandler.getDevice(centerId,command.getContentValue3()).getBigDeviceFlag() != 0){
							DBHandler.writeBigDeviceRecord(value, centerId, command);
						}
					}
					dtmp = DBHandler.getLastDeviceTmp(centerId, command);
					DBHandler.updateCenterAfterRead(centerId);// 更新集中器的读表记录
					DBHandler.deletePlanQueueBefore(command.getContentValue1());

				} else {
					DBHandler.updateCmd_PL(command, 6);
				}
				logger.info(ss2);
			} catch (SocketException e) {
				DBHandler.updateCmd_PL(command, 1);
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
