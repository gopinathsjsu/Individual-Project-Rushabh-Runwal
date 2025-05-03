package com.logparser;

import com.logparser.builder.HandlerChainBuilder;
import com.logparser.handler.*;
import com.logparser.processor.LogProcessor;
import com.logparser.util.JSONWriter;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar log-parser.jar <logFilePath>");
            System.exit(1);
        }

        String logFilePath = args[0];
        LogHandler handlerChain = HandlerChainBuilder.buildChain();

        // Extract references to individual handlers
        APMHandler apmHandler = (APMHandler) handlerChain;
        ApplicationHandler appHandler = (ApplicationHandler) apmHandler.getNext();
        RequestHandler requestHandler = (RequestHandler) appHandler.getNext();

        LogProcessor processor = new LogProcessor(handlerChain);
        processor.process(logFilePath);

        JSONWriter jsonWriter = new JSONWriter();

        // Write APM metrics to JSON
        Map<String, Map<String, Double>> apmReport = apmHandler.generateAPMReport();
        jsonWriter.writeToJson("output/apm.json", apmReport);

        Map<String, Map<String, Object>> requestSummary = requestHandler.generateRequestReport();
        jsonWriter.writeToJson("output/request.json", requestSummary);

        Map<String, Integer> appSummary = appHandler.getSeverityCounts();
        jsonWriter.writeToJson("output/application.json", appSummary);

        System.out.println("All processing complete!");
    }

}
