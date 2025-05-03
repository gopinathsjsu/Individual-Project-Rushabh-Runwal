package com.logparser.handler;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestHandlerTest {

    @Test
    void testProcessValidLine() {
        RequestHandler handler = new RequestHandler();
        String logLine = "timestamp=2024-12-02T10:00:00Z request_method=GET request_url=/api/status response_status=200 response_time_ms=150";

        handler.process(logLine);

        Map<String, Map<String, Object>> report = handler.generateRequestReport();
        assertTrue(report.containsKey("/api/status"));

        Map<String, Object> routeReport = report.get("/api/status");
        assertEquals(150, routeReport.get("min_response_time"));
        assertEquals(150.0, routeReport.get("average_response_time"));
        assertEquals(150, routeReport.get("max_response_time"));

        Map<String, Integer> statusCounts = (Map<String, Integer>) routeReport.get("status_counts");
        assertEquals(1, statusCounts.get("2XX"));
    }

    @Test
    void testPercentileCalculation() {
        RequestHandler handler = new RequestHandler();
        handler.process("request_method=GET request_url=/api/data response_status=200 response_time_ms=100");
        handler.process("request_method=GET request_url=/api/data response_status=200 response_time_ms=200");
        handler.process("request_method=GET request_url=/api/data response_status=200 response_time_ms=300");

        Map<String, Map<String, Object>> report = handler.generateRequestReport();
        Map<String, Object> routeReport = report.get("/api/data");
        Map<String, Double> percentiles = (Map<String, Double>) routeReport.get("percentiles");

        assertEquals(200, percentiles.get("50th"));
    }
}
