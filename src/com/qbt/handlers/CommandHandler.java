package com.qbt.handlers;

import com.qbt.entity.Command;

public abstract class CommandHandler implements Runnable {

	protected int timeOut;
	protected Command command;
	protected long startTime;
	protected long endTime;
	
	public CommandHandler(Command command) {
		super();
		this.timeOut = 10000;
		this.command = command;
	}

	public CommandHandler(Command command,int timeOut) {
		super();
		this.timeOut = timeOut;
		this.command = command;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public abstract void run();
}

