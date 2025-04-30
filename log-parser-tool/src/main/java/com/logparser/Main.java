package com.logparser;

public class Main {
    public static void main(String[] args) {

        String inputFilePath = null; // Input file must be specified with --file
        String apmOutputPath = "output/apm.json";
        String applicationOutputPath = "output/application.json";
        String requestOutputPath = "output/request.json";


        // Parse command-line arguments
        // args contains the command line arguments passed to the main function
        for (int i = 0; i < args.length; i++) {
            if ("--file".equals(args[i])) {
                inputFilePath = args[++i];
            } else {
                System.err.println("Unknown argument: " + args[i]);
            }
        }

        // check if we got a file path
        if (inputFilePath == null) {
            System.err.println("Error: Please specify an input file using --file <filename.txt>");
            return;
        }

        System.out.println(".. Generating Report");

    }
}