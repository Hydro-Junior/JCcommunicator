package com.qbt.handlers;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.qbt.entity.Center;
import com.qbt.entity.DataOfReq;
import com.qbt.entity.Request;
import com.qbt.util.GsonUtil;
import com.qbt.util.ReturnGetter;

public class OnlineChecker implements Runnable {

	@Override
	public void run()  {
		Logger logger = Logger.getLogger(OnlineChecker.class);
		logger.info("监控程序启动！");
		while(true){
			List<Center> centers = new ArrayList();
			centers = DBHandler.findAllCenter();
			/**
			 * 根据远程监测结果，依次更新数据库中集中器的在线状态
			 */
			for(Center ct : centers){
				Request req = new Request("JZQ_QUERYSTATUS",new DataOfReq(ct.getGprsNum()));
				String s = GsonUtil.objToJson(req);
				try {
					String ss = (String)ReturnGetter.getReturn(s);
					JSONObject jo = new JSONObject(ss);
					if(jo.getJSONObject("error").getString("code").equals("0")){
						if(jo.getJSONObject("data").getString("remote_ip").equals("")){
							DBHandler.updateCenterState(0, ct.getGprsNum());
							System.out.println("集中器："+ ct.getGprsNum()+" 不在线");
						}else{
							DBHandler.updateCenterState(1, ct.getGprsNum());
							System.out.println("集中器："+ct.getGprsNum()+" 在线");
							//更新数据库中的心跳时间
							DBHandler.updateHeartBeatTime(ct.getGprsNum());
						}
					}else{
						DBHandler.updateCenterState(0, ct.getGprsNum());
					}
					logger.info(ss);
				} catch (SocketTimeoutException | NullPointerException e) {
					logger.error(e.toString());
					DBHandler.updateCenterState(0, ct.getGprsNum());
					System.out.println("连接超时，可能是没有得到返回值！");
					break;
				} catch (Exception e) {
					DBHandler.updateCenterState(0, ct.getGprsNum());
					logger.error(e.toString());
					e.printStackTrace();
				}
			}
			try {
				TimeUnit.MINUTES.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
