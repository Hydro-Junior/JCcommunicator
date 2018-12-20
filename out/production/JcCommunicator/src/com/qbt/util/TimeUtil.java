package com.qbt.util;

import java.util.Calendar;

public class TimeUtil {
	public static String getNowTime_Chinese(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 
		String nowTime = year+"年"+month+"月"+date+"日"+hour+"时"+minute+"分"+second+"秒";
		return nowTime;
	}
}