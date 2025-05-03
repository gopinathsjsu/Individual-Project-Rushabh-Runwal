package com.logparser.model;

import java.util.Date;

public class APMLogEntry implements LogEntry {
    private final String metric;
    private final double value;
    private final Date timestamp;

    public APMLogEntry(String metric, double value, Date timestamp) {
        this.metric = metric;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getMetric() {
        return metric;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
