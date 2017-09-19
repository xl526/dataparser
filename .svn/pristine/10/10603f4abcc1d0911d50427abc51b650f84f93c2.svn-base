package com.ts.zx.ig.fbox.client;
import java.net.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ts.zx.ig.fbox.conn.ServerCaller;

public class Global {
    public static Proxy proxy = null; // new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
    public static ExecutorService threadPool = Executors.newCachedThreadPool();
    public static ServerCaller commServer;
    public static ServerCaller appServer;

    public static final String idServerUrl = "https://account.flexem.com/core/";
    public static String appServerApiUrl = "http://fbox360.com/api/client/";
    public static final String commServerApiUrl = "http://fbcs101.fbox360.com/api/";
    public static final String commServerSignalRUrl = "http://fbcs101.fbox360.com/push";
    public static String signalrClientId = UUID.randomUUID().toString();

    public static String username = "wuhandongpu";
    public static String password = "123456";
    public static String clientId = "whdp";
    public static String clientSecret = "fcb85216f96f43a99c59583c6bec88e8";
}
