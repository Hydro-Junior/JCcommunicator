package com.qbt.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.qbt.handlers.CommandDistributer;
import com.qbt.handlers.DBHandler;
import com.qbt.handlers.OnlineChecker;

public class Main {
	public static void main(String[] args){
		/**
		 * 监控线程类和任务分派线程池（主方法）
		 */
		OnlineChecker onlineChecker = new OnlineChecker();
		Thread monitor =new Thread(onlineChecker);
		monitor.start();
		CommandDistributer taskDistributer = new CommandDistributer();
		taskDistributer.init();
		Thread commandExcecuter = new Thread(taskDistributer);
		commandExcecuter.start();
	    
	}
}
