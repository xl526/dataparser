package com.ts.zx.ig.fbox;


public class FBoxClient {
	
	public static void main(String[] args) {
		
		String accessToken = FBoxHelper.login();
		SignalRConnectUtil.beginConnect(FBoxConfig.SIGNALR_URL, accessToken, FBoxConfig.X_FBOX_CLIENT_ID);
	}
}
