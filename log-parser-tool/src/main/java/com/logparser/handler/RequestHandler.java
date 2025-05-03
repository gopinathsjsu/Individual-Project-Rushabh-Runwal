package com.logparser.handler;

import java.util.*;

public class RequestHandler extends LogHandler {

    private final Map<String, List<Integer>> responseTimes = new HashMap<>();
    private final Map<String, Map<String, Integer>> statusCounts = new HashMap<>();

    @Override
    protected boolean canHandle(String line) {
        return line.contains("request_method=") && line.contains("response_status=") && line.contains("response_time_ms=");
    }

    @Override
    protected void process(String line) {
        try {
            String requestUrl = null;
            int latency = 0;
            int responseStatus = 0;

            String[] parts = line.split(" ");
            for (String part : parts) {
                if (part.startsWith("request_url=")) {
                    requestUrl = part.split("=")[1].replace("\"", "");
                } else if (part.startsWith("response_time_ms=")) {
                    latency = Integer.parseInt(part.split("=")[1]);
                } else if (part.startsWith("response_status=")) {
                    responseStatus = Integer.parseInt(part.split("=")[1]);
                }
            }

            if (requestUrl != null) {
                responseTimes.computeIfAbsent(requestUrl, k -> new ArrayList<>()).add(latency);

                String category = fetchCategory(responseStatus);
                statusCounts.computeIfAbsent(requestUrl, k -> new HashMap<>());
                Map<String, Integer> categoryMap = statusCounts.get(requestUrl);
                categoryMap.put(category, categoryMap.getOrDefault(category, 0) + 1);

                System.out.println("RequestHandler processed URL: " + requestUrl + ", latency: " + latency + "ms, status: " + responseStatus);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error processing request log line: " + line + " - " + e.getMessage());
        }
    }

    private String fetchCategory(int status) {
        if (status >= 200 && status < 300) return "2XX";
        else if (status >= 400 && status < 500) return "4XX";
        else return "5XX";
    }

    public Map<String, Map<String, Object>> generateRequestReport() {
        Map<String, Map<String, Object>> report = new HashMap<>();

        for (String route : responseTimes.keySet()) {
            List<Integer> times = responseTimes.get(route);
            Map<String, Object> routeReport = new HashMap<>();

            double avg = times.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            int min = times.stream().mapToInt(Integer::intValue).min().orElse(0);
            int max = times.stream().mapToInt(Integer::intValue).max().orElse(0);
            Map<String, Double> percentiles = calculatePercentiles(times);

            routeReport.put("min_response_time", min);
            routeReport.put("average_response_time", avg);
            routeReport.put("max_response_time", max);
            routeReport.put("percentiles", percentiles);
            routeReport.put("status_counts", statusCounts.get(route));

            report.put(route, routeReport);
        }

        return report;
    }

    private Map<String, Double> calculatePercentiles(List<Integer> times) {
        List<Integer> sorted = new ArrayList<>(times);
        Collections.sort(sorted);

        Map<String, Double> percentiles = new HashMap<>();
        percentiles.put("50th", getPercentile(sorted, 50));
        percentiles.put("90th", getPercentile(sorted, 90));
        percentiles.put("95th", getPercentile(sorted, 95));
        percentiles.put("99th", getPercentile(sorted, 99));

        return percentiles;
    }

    private double getPercentile(List<Integer> sorted, int percentile) {
        if (sorted.isEmpty()) return 0;
        int index = (int) Math.ceil(percentile / 100.0 * sorted.size()) - 1;
        index = Math.max(0, Math.min(index, sorted.size() - 1));
        return sorted.get(index);
    }
}
