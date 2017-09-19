package com.ts.zx.ig.fbox.client;
import java.io.IOException;
import java.net.Proxy;

import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ts.zx.ig.AQHandler;
import com.ts.zx.ig.fbox.conn.LoggerFactory;
import com.ts.zx.ig.fbox.conn.TokenManager;
import com.ts.zx.ig.fbox.conn.models.BoxStateChanged;
import com.ts.zx.ig.fbox.conn.signalr.SignalRConnectionBase;

import microsoft.aspnet.signalr.client.hubs.HubProxy;

public class FBoxSignalRConnection extends SignalRConnectionBase {

    private final Gson gson;
    private final com.ts.zx.ig.fbox.conn.Logger logger;
    private Proxy proxy;

    public FBoxSignalRConnection(String hubUrl, String signalrClientId, TokenManager tokenManager, Proxy proxy, LoggerFactory loggerFactory) {
        super(hubUrl, signalrClientId, tokenManager, proxy, loggerFactory);
        this.logger = loggerFactory.createLogger("FBoxSignalRConnection");
        this.proxy = proxy;
        gson = new GsonBuilder().create();
    }

    @Override
    protected void onHubProxyCreated(HubProxy hubProxy) {
        hubProxy.subscribe("dmonUpdateValue").addReceivedHandler(jsonElements -> {
            Global.threadPool.submit(() -> {
                System.out.println("dmon data received: ");
                StringBuffer buffer = new StringBuffer();
                buffer.append("[");
                buffer.append(StringUtils.join(jsonElements, ","));
                buffer.append("]");
                try {
					AQHandler.send(buffer.toString());
				} catch (JMSException e) {
					e.printStackTrace();
				}
                /*for (com.google.gson.JsonElement jsonElement : jsonElements) {
//                    System.out.println("\t" + jsonElement);
                	System.out.println("my data..." + jsonElement.toString());
                }*/
            });
        });

        hubProxy.subscribe("boxConnStateChanged").addReceivedHandler(jsonElements -> {
            Global.threadPool.submit(() -> {
                System.out.println("Box state changed.");
                if (jsonElements.length <= 0)
                    return;
                BoxStateChanged[] stateChanges = gson.fromJson(jsonElements[0], BoxStateChanged[].class);
                for (BoxStateChanged stateChange : stateChanges) {
                    try {
                        Global.commServer.executePost("box/" + stateChange.id + "/dmon/start", String.class);
                        System.out.println("Start dmon points on box " + stateChange.id + " ok.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
//            }

//            Global.threadPool.submit(() -> {
//                try {
//                    BoxGroup[] boxGroups = Global.appServer.executeGet("box/grouped", BoxGroup[].class);
//
//                    for (BoxGroup group : boxGroups) {
//                        for (BoxReg boxReg : group.boxRegs) {
//                            String boxNo = boxReg.box.boxNo;
//                            Global.commServer.executePost("dmon/start?boxNo=" + boxNo, String.class);
//                            System.out.println("Start dmon points on box " + boxNo + " ok.");
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
    }
}
