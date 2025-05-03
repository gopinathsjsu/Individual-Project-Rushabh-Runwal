package com.logparser.handler;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class APMHandlerTest {

    @Test
    void testProcessValidLine() {
        APMHandler handler = new APMHandler();
        String logLine = "timestamp=2024-12-02T10:00:00Z metric=cpu_usage_percent value=72.5";

        handler.process(logLine);

        Map<String, List<Double>> metrics = handler.getMetrics();
        assertTrue(metrics.containsKey("cpu_usage_percent"));
        assertEquals(1, metrics.get("cpu_usage_percent").size());
        assertEquals(72.5, metrics.get("cpu_usage_percent").get(0));
    }

    @Test
    void testGenerateAPMReport() {
        APMHandler handler = new APMHandler();
        handler.process("metric=cpu_usage_percent value=50");
        handler.process("metric=cpu_usage_percent value=100");

        Map<String, Map<String, Double>> report = handler.generateAPMReport();
        Map<String, Double> stats = report.get("cpu_usage_percent");

        assertEquals(50, stats.get("min"));
        assertEquals(100, stats.get("max"));
        assertEquals(75, stats.get("average"));
        assertEquals(75, stats.get("median"));
    }
}
