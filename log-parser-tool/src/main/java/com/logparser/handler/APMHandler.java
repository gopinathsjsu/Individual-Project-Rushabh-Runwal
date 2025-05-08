package com.logparser.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APMHandler extends LogHandler {

    private final Map<String, List<Double>> metrics = new HashMap<>();

    @Override
    protected boolean canHandle(String line) {
        return line.contains("metric=") && line.contains("value=");
    }

    @Override
    protected void process(String line) {
        try {
            String[] parts = line.split(" ");
            String metricName = null;
            double value = 0.0;

            for (String part : parts) {
                if (part.startsWith("metric=")) {
                    metricName = part.split("=")[1];
                } else if (part.startsWith("value=")) {
                    value = Double.parseDouble(part.split("=")[1]);
                }
            }

            if (metricName != null) {
                metrics.computeIfAbsent(metricName, k -> new ArrayList<>()).add(value);
                System.out.println("APMHandler processed metric: " + metricName + ", value: " + value);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error processing APM log line: " + line + " - " + e.getMessage());
        }
    }

    public Map<String, Map<String, Double>> generateAPMReport() {
        Map<String, Map<String, Double>> report = new HashMap<>();

        for (Map.Entry<String, List<Double>> entry : metrics.entrySet()) {
            String metric = entry.getKey();
            List<Double> values = entry.getValue();

            double min = values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
            double max = values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
            double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double median = calculateMedian(values);

            Map<String, Double> stats = new HashMap<>();
            stats.put("minimum", min);
            stats.put("maximum", max);
            stats.put("average", avg);
            stats.put("median", median);

            report.put(metric, stats);
        }
        return report;
    }

    private double calculateMedian(List<Double> values) {
        if (values.isEmpty()) return 0.0;

        List<Double> sorted = new ArrayList<>(values);
        sorted.sort(Double::compareTo);

        int middle = sorted.size() / 2;
        if (sorted.size() % 2 == 0) {
            return (sorted.get(middle - 1) + sorted.get(middle)) / 2.0;
        } else {
            return sorted.get(middle);
        }
    }

    public Map<String, List<Double>> getMetrics() {
        return metrics;
    }
}
