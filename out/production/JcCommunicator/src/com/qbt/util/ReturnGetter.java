package com.qbt.util;

import com.qbt.entity.Constants;

public class ReturnGetter {
	public static Object getReturn(String param) throws Exception {
		String wsdl = Constants.WsdlURL;
		WebServerHelper helper = new WebServerHelper(wsdl , "OperationRequest",
				new Object[] { param });
		Object obj = helper.sendByWebServer();
		return obj;
	}

}
