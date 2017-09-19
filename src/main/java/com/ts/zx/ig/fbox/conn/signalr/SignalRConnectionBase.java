package com.ts.zx.ig.fbox.conn.signalr;

import java.io.IOException;
import java.net.Proxy;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

import com.ts.zx.ig.fbox.conn.LoggerFactory;
import com.ts.zx.ig.fbox.conn.LoginFailedException;
import com.ts.zx.ig.fbox.conn.TokenManager;
import com.ts.zx.ig.fbox.conn.signalr.internal.JavaHttpConnection;

import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

public abstract class SignalRConnectionBase {
    private final Semaphore connectEvent;
    private final HubConnection hubConnection;
    private final HubProxy hubProxy;
    private final com.ts.zx.ig.fbox.conn.Logger logger;
    private Proxy proxy;
    private final LoggerFactory loggerFactory;
    private final SignalRLoggerWrapper javaconnLogger;
    private final SignalRLoggerWrapper sseLogger;
    private TokenManager tokenManager;
    private String accessToken;
    private boolean shouldGetNewToken;
    private int retrycount;
    private long lastConnectedTime = 0;

    public SignalRConnectionBase(String hubUrl, String signalrClientId, TokenManager tokenManager, Proxy proxy, LoggerFactory loggerFactory) {
        this.proxy = proxy;
        this.loggerFactory = loggerFactory;
        this.logger = loggerFactory.createLogger("SignalRConnectionBase");
        this.sseLogger = new SignalRLoggerWrapper(this.loggerFactory.createLogger("ServerSentEventsTransport"));
        this.javaconnLogger = new SignalRLoggerWrapper(this.loggerFactory.createLogger("SignalRTransportConnection"));
        this.tokenManager = tokenManager;
        this.hubConnection = new HubConnection(hubUrl, "cid=" + signalrClientId, true, new SignalRLoggerWrapper(loggerFactory.createLogger("SignalR")));
        this.hubConnection.error(throwable -> onConnectionError(throwable));
        this.hubConnection.reconnected(() -> onReconnected());
        this.hubConnection.closed(() -> onConnectionClosed());
        this.hubProxy = this.hubConnection.createHubProxy("clienthub");
        this.connectEvent = new Semaphore(1);
        this.onHubProxyCreated(hubProxy);
        new Thread(() -> {
            try {
                SignalRConnectWorker();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (LoginFailedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void onReconnected() {
        this.logger.logInformation("Reconnected. hubconn=" + this.hubConnection.getUrl());
    }

    private void onConnectionClosed() {
        this.logger.logInformation("Closed. hubconn=" + this.hubConnection.getUrl());
        this.connectEvent.release();
    }

    private void onConnectionError(Throwable e) {
        this.logger.logInformation("Error. exception=" + e.toString() + "hubconn=" + this.hubConnection.getUrl());
        if (e.getCause().getMessage().contains("401")) {
            this.shouldGetNewToken = true;
        }

        this.hubConnection.stop();
    }

    protected abstract void onHubProxyCreated(HubProxy hubProxy);

    public void connected(Runnable handler) {
        this.logger.logInformation("Connected. hubconn=" + this.hubConnection.getUrl());
        hubConnection.connected(handler);
    }

    private void UpdateToken() throws LoginFailedException, InterruptedException {
        int retryCount2 = 0;
        for (; ; ) {
            try {
                this.accessToken = this.tokenManager.getOrUpdateToken(this.accessToken);
                break;
            } catch (LoginFailedException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
                int waitTime2 = retryCount2++ * 1000;
                if (waitTime2 > 300000) {
                    waitTime2 = 5000;
                }
                Thread.sleep(waitTime2);
            }
        }
    }

    private void SignalRConnectWorker() throws InterruptedException, LoginFailedException {
        for (; ; ) {
            do {
                this.connectEvent.acquire();
            } while (new Date().getTime() - this.lastConnectedTime < 5000);

            if (this.accessToken == null || shouldGetNewToken)
                UpdateToken();
            this.hubConnection.setCredentials(request -> request.addHeader("Authorization", "Bearer " + this.accessToken));
            try {
                this.hubConnection.start(new ServerSentEventsTransport(this.sseLogger, new JavaHttpConnection(this.proxy, this.javaconnLogger))).get();
                this.retrycount = 0;
                this.shouldGetNewToken = false;
                this.lastConnectedTime = new Date().getTime();
            } catch (ExecutionException e) {
                e.printStackTrace();
                if (e.getCause().getMessage().contains("401")) {
                    this.shouldGetNewToken = true;
                }
                int waitTime = this.retrycount++ * 1000;
                if (waitTime > 300000) {
                    waitTime = 5000;
                }

                if (waitTime > 0)
                    Thread.sleep(waitTime);
            }
        }
    }

    public void start() {
        this.connectEvent.release();
    }
}
