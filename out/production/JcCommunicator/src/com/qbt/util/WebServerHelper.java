package com.qbt.util;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServerHelper {
	
	/**
	 * 调用webService的url
	 */
	private String wsdlUrl;
	
	/**
	 * 对应的方法名
	 */
	private String methodName;
	
	/**
	 * 传送的参数
	 */
	private Object[] params;
	
	/**
	 * 访问名
	 */
	private String namespace = "http://www.cqct.com/webservice/";

	/**
	 * 通过构造方法接收参数
	 * @param wsdlUrl webService的url
	 * @param methodName webService的方法名
	 * @param params webService的参数
	 */
	public WebServerHelper(String wsdlUrl, String methodName,Object[] params){
		this.wsdlUrl = wsdlUrl;
		this.methodName = methodName;
		this.params = params;
	}
	
	/**
	 * 调用WEBSERVICE执行方法
	 * @return WEBSERVICE返回的结果
	 */
	public Object sendByWebServer() throws Exception{
		try {
			SoapObject result = getSendSoapObject(params);
			if(result != null && result.getPropertyCount() == 1) {
				return result.getProperty(0).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 调用WEBSERVICE执行方法，取得返回的数组
	 * @return WEBSERVICE返回的结果
	 */
	public Object[] sendByArrayWebServer() throws Exception{
		SoapObject result = getSendSoapObject(params);
		if(result != null) {
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < result.getPropertyCount(); i++) {
				list.add(result.getProperty(i).toString());
			}
			
			return list.toArray() ;
		}
		
		return null;
	}
	
	private SoapObject getSendSoapObject(Object... params) throws Exception{
		SoapObject request = new SoapObject(namespace, methodName);
		
		
		//设置参数信息
		if(params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				request.addProperty("param" + i, params[i]);
			}
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		
		envelope.bodyOut = request;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		envelope.encodingStyle = "UTF-8";
		
		HttpTransportSE ht = new HttpTransportSE(wsdlUrl,60000);
		ht.debug = true;
		ht.call(namespace + methodName, envelope);
		
		if (envelope.getResponse() != null) {
			return (SoapObject) envelope.bodyIn;
		}
		
		return null;
	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
