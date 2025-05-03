package com.logparser.handler;

public class UnhandledHandler extends LogHandler {
    @Override
    protected boolean canHandle(String line) {
        return true; // fallback: handles anything not caught earlier
    }

    @Override
    protected void process(String line) {
        System.out.println("Unhandled log line: " + line);
    }
}
