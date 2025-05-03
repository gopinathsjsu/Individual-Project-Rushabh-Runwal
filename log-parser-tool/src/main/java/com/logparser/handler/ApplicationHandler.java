package com.logparser.handler;

import java.util.HashMap;
import java.util.Map;

public class ApplicationHandler extends LogHandler {

    private final Map<String, Integer> severityCounts = new HashMap<>();

    @Override
    protected boolean canHandle(String line) {
        return line.contains("level=") && line.contains("message=");
    }

    @Override
    protected void process(String line) {
        try {
            String severity = null;
            String message = "No message";

            String[] parts = line.split(" ");
            for (String part : parts) {
                if (part.startsWith("level=")) {
                    severity = part.split("=")[1];
                } else if (part.startsWith("message=")) {
                    message = part.substring(part.indexOf("message=") + 8);
                }
            }

            if (severity != null) {
                severityCounts.put(severity, severityCounts.getOrDefault(severity, 0) + 1);
                System.out.println("Application Log → Severity: " + severity + ", Message: " + message);
            }
        } catch (Exception e) {
            System.err.println("Error processing application log: " + line + " - " + e.getMessage());
        }
    }

    public Map<String, Integer> getSeverityCounts() {
        return severityCounts;
    }
}
