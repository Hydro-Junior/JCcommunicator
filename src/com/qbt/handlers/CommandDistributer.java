package com.qbt.handlers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qbt.entity.Command;
import com.qbt.handlers.CommandFactory;
import com.qbt.handlers.CommandHandler;
import com.qbt.handlers.DBHandler;

public class CommandDistributer implements Runnable {
	private boolean isRun = false;
	public void init(){
		isRun = true;
	}
	@Override
	public void run() {
		while(isRun){
			//建立固定线程池
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
			//获得临时命令
			List<Command> cmdTmpList = DBHandler.getTempCommand();
			if(!cmdTmpList.isEmpty()){
				for(Command cmd : cmdTmpList){
					CommandHandler cmdHandler = CommandFactory.create(cmd);
					fixedThreadPool.execute(cmdHandler);
				}
			}
			//获得计划命令
			List<Command> cmdList = DBHandler.getPlanCommand();
			if(!cmdList.isEmpty()){
				for(Command cmd : cmdList){
					CommandHandler cmdHandler = CommandFactory.create(cmd);
					fixedThreadPool.execute(cmdHandler);		
				}
			}
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}

