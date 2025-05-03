package com.logparser.builder;

import com.logparser.handler.*;

public class HandlerChainBuilder {
    public static LogHandler buildChain() {
        LogHandler apmHandler = new APMHandler();
        LogHandler appHandler = new ApplicationHandler();
        LogHandler requestHandler = new RequestHandler();
        LogHandler unhandledHandler = new UnhandledHandler();

        apmHandler.setNext(appHandler);
        appHandler.setNext(requestHandler);
        requestHandler.setNext(unhandledHandler);

        return apmHandler;
    }
}
