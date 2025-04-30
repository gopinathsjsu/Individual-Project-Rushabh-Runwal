package com.logparser.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static void writeToJSONFile(String filePath, Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);

            // Ensure the parent directory exists if it is not null
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}