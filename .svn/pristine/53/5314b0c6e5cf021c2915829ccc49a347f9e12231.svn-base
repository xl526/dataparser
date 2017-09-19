package com.ts.zx.ig.fbox;

import javax.jms.JMSException;

import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.NullLogger;
import microsoft.aspnet.signalr.client.hubs.HubConnection;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.ts.zx.ig.AQHandler;
import com.ts.zx.ig.XMLUtils;

public class SignalRConnectUtil {
	public static HubConnection beginConnect(String url, String token,
			String clientId) {

		HubConnection connection = new HubConnection(url, "at=" + token
				+ "&cid=" + clientId, true, new NullLogger());
		connection.createHubProxy("clienthub");

		connection.connected(new Runnable() {

			@Override
			public void run() {
				
				System.out.println("the signalR is connected...");
//				SignalRTest.isConneted=true;
			}
		});
		connection.error(new ErrorCallback() {

			@Override
			public void onError(Throwable error) {
				
//				SignalRTest.isConneted=false;
				System.out.println("error");
				System.out.println(error.toString());
				if (error != null && error.toString().contains("401")) {
					
				}

			}
		});
		connection.closed(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("the signalR is closed...");
//				SignalRTest.isConneted=false;
				
			}
		});
		connection.received(new MessageReceivedHandler() {

			@Override
			public void onMessageReceived(JsonElement arg0) {
				//if(arg0.getAsJsonObject().get("M").equals("dMonUpdateValue"))
				System.out.println(arg0.toString());
				//解析存储到数据库
				JSONObject json = JSONObject.parseObject(arg0.toString());
				if ("dMonUpdateValue".equals(json.getString("M"))) {
					String text = json.getString("A");
					String equipmentsXML = XMLUtils.fboxData2XML(text);
					System.out.println(equipmentsXML);
					
					try {
						AQHandler.send(equipmentsXML);
					} catch (JMSException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		});
		connection.start();
		return connection;
	}

}
