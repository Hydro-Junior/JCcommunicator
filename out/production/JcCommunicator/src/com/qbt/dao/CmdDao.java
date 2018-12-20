package com.qbt.dao;

import java.util.List;

import com.qbt.entity.Command;

public interface CmdDao {
	//查询计划命令
	public List<Command> queryCommand_PL();
	//查询临时命令
	public List<Command> queryCommand_TP();
	//更新计划命令的状态
	public boolean updateCommandState_PL(Command c,int s);
	//更新临时命令的状态
	public boolean updateCommandState_TP(Command c,int s);
	//删除计划命令
	public boolean delCommand_PL(Command c);
	//删除临时命令
	public boolean delCommand_TP(Command c);
	//批量删除计划命令
	public boolean delPlanQueueBefore(String gprs);
	//读取集中器信息到临时命令表
	public boolean updateCenterInfo_TP(Command c,String scheme);
}