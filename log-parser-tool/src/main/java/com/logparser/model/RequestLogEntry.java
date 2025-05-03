package com.logparser.model;

import java.util.Date;

public class RequestLogEntry implements LogEntry {
    private String method;
    private String url;
    private int status;
    private double latency;
    private Date timestamp;

    public RequestLogEntry(String method, String url, int status, double latency, Date timestamp) {
        this.method = method;
        this.url = url;
        this.status = status;
        this.latency = latency;
        this.timestamp = timestamp;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public double getLatency() {
        return latency;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
