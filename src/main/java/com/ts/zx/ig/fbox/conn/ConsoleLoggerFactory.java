package com.ts.zx.ig.fbox.conn;

public class ConsoleLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger(String name) {
        return new ConsoleLogger(name);
    }
}
