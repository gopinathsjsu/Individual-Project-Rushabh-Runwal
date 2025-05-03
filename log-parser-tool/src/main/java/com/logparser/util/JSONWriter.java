package com.logparser.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public void writeToJson(String fileName, Object data) {
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}
