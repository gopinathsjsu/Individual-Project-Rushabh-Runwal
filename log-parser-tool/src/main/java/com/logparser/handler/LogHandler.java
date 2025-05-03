package com.logparser.handler;

public abstract class LogHandler {
    protected LogHandler next;

    public void setNext(LogHandler next) {
        this.next = next;
    }

    public LogHandler getNext() {
        return next;
    }

    public boolean handle(String line) {
        if (canHandle(line)) {
            process(line);
            return true;
        } else if (next != null) {
            return next.handle(line);
        }
        return false;
    }

    protected abstract boolean canHandle(String line);

    protected abstract void process(String line);
}
