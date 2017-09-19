package com.ts.zx.ig.fbox.conn.signalr;

import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;

public class SignalRLoggerWrapper implements Logger {

    private final com.ts.zx.ig.fbox.conn.Logger logger;

    public SignalRLoggerWrapper(com.ts.zx.ig.fbox.conn.Logger logger){
        this.logger = logger;
    }
    @Override
    public void log(String message, LogLevel level) {
        switch (level) {
            case Critical:
                this.logger.logError(message);
                break;
            case Information:
                this.logger.logInformation(message);
                break;
            case Verbose:
                this.logger.logTrace(message);
                break;
        }
    }

}
