package com.qbt.util;

import com.google.gson.Gson;

public class GsonUtil {
	public static String objToJson(Object b){
		Gson gson = new Gson();
		return gson.toJson(b).toString();
	}
}
