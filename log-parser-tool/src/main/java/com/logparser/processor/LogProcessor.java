package com.logparser.processor;

import com.logparser.handler.LogHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogProcessor {
    private final LogHandler handlerChain;

    public LogProcessor(LogHandler handlerChain) {
        this.handlerChain = handlerChain;
    }

    public void process(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder logBlock = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("timestamp=") && logBlock.length() > 0) {
                    // Process the previous block
                    handleBlock(logBlock.toString().trim());
                    logBlock.setLength(0); // Reset
                }
                logBlock.append(line).append(" ");
            }

            // Process the last block
            if (logBlock.length() > 0) {
                handleBlock(logBlock.toString().trim());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void handleBlock(String block) {
        boolean handled = handlerChain.handle(block);
        if (!handled) {
            System.out.println("Unhandled log block: " + block);
        }
    }
}
