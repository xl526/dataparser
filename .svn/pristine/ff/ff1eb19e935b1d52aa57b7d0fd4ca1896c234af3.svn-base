package com.ts.zx.ig.fbox.client;
import java.io.IOException;
import java.util.Scanner;

import com.ts.zx.ig.fbox.conn.ConsoleLoggerFactory;
import com.ts.zx.ig.fbox.conn.ServerCaller;
import com.ts.zx.ig.fbox.conn.StaticCredentialProvider;
import com.ts.zx.ig.fbox.conn.TokenManager;
import com.ts.zx.ig.fbox.conn.models.BoxGroup;
import com.ts.zx.ig.fbox.conn.models.BoxReg;

public class Main {
    public static void main(String[] args) {
        ConsoleLoggerFactory loggerFactory = new ConsoleLoggerFactory();
        TokenManager tokenManager = new TokenManager(new StaticCredentialProvider(Global.clientId, Global.clientSecret, Global.username, Global.password), Global.idServerUrl, loggerFactory);

        ServerCaller commServer = new ServerCaller(tokenManager, Global.commServerApiUrl, Global.signalrClientId, loggerFactory);
        ServerCaller appServer = new ServerCaller(tokenManager, Global.appServerApiUrl, Global.signalrClientId, loggerFactory);

        Global.commServer = commServer;
        Global.appServer = appServer;

        FBoxSignalRConnection fboxSignalR = new FBoxSignalRConnection(Global.commServerSignalRUrl, Global.signalrClientId, tokenManager, Global.proxy, loggerFactory);

        fboxSignalR.connected(() -> {
        });
        fboxSignalR.start();

        System.out.println("Box list:");
        try {
            BoxGroup[] boxGroups = Global.appServer.executeGet("box/grouped", BoxGroup[].class);

            for (BoxGroup group : boxGroups) {
                for (BoxReg boxReg : group.boxRegs) {
                    System.out.printf("\t%s\t%s\t%s\n", boxReg.alias, boxReg.box.boxNo, boxReg.box.boxType);
                    String boxNo = boxReg.box.boxNo;
                    Global.commServer.executePost("dmon/start?boxNo=" + boxNo, String.class);
                    System.out.println("Start dmon points on box " + boxNo + " ok.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
