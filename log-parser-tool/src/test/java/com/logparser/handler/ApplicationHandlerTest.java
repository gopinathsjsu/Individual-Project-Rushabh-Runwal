package com.logparser.handler;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationHandlerTest {

    @Test
    void testProcessValidLine() {
        ApplicationHandler handler = new ApplicationHandler();
        String logLine = "timestamp=2024-12-02T10:00:00Z level=INFO message=\"User updated profile\"";

        handler.process(logLine);

        Map<String, Integer> counts = handler.getSeverityCounts();
        assertTrue(counts.containsKey("INFO"));
        assertEquals(1, counts.get("INFO"));
    }

    @Test
    void testProcessMultipleSeverities() {
        ApplicationHandler handler = new ApplicationHandler();
        handler.process("level=INFO message=\"info message\"");
        handler.process("level=ERROR message=\"error message\"");
        handler.process("level=INFO message=\"another info\"");

        Map<String, Integer> counts = handler.getSeverityCounts();
        assertEquals(2, counts.get("INFO"));
        assertEquals(1, counts.get("ERROR"));
    }
}
