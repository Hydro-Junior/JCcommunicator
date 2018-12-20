package com.qbt.commandsolutions;

import com.qbt.entity.Command;
import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;

public class CenterInfoReader extends CommandHandler {

	public CenterInfoReader(Command command) {
		super(command);
	}
	public CenterInfoReader(Command command,int timeOut) {
		super(command,timeOut);
	}

	@Override
	public void run() {
		DBHandler.updateCmd_TP(command, 3);
		DBHandler.readCenterInfo(command, "默认抄表方案");
		DBHandler.updateCmd_TP(command, 5); //执行成功
		
	}

}
