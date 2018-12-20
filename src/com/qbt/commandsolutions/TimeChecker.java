package com.qbt.commandsolutions;

import com.qbt.entity.Command;
import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;

public class TimeChecker extends CommandHandler {

	public TimeChecker(Command command) {
		super(command);
	}

	@Override
	public void run() {
		switch(this.command.getType()){
		case 0:
			DBHandler.updateCmd_TP(command, 3);
			
			DBHandler.updateCmd_TP(command, 5); //执行成功
		
		case 1:
		default:break;
		}
	}

}

