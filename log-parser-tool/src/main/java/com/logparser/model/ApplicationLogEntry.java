package com.logparser.model;

import java.util.Date;

public class ApplicationLogEntry implements LogEntry {
    private String severity;
    private String message;
    private Date timestamp;

    public ApplicationLogEntry(String severity, String message, Date timestamp) {
        this.severity = severity;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
