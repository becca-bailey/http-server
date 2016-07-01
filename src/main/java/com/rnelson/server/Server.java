package com.rnelson.server;

import application.Config;

import java.io.File;

public class Server {

    public static Boolean isPortParameter(String parameter) {
        return (parameter.equals("-p"));
    }

    private static void validateArgs(String[] args) {
        if (args.length > 0) {
            if (!isPortParameter(args[0])) {
                System.err.println("Usage: java -jar package/http-server-0.0.1.jar -p <port number>");
            }
            try {
                Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.err.println("Usage: java -jar package/http-server-0.0.1.jar -p <port number>");
            }
        }
    }

    public static Integer getPortNumber(String[] args) {
        validateArgs(args);
        int portNumber;
        if (args.length > 0) {
            portNumber = Integer.parseInt(args[1]);
        }
        else {
            portNumber = 5000;
        }
        return portNumber;
    }

    public static void main(String[] args) throws Exception {
        Config.packageName = "application";
        Config.rootDirectory = new File("src/main/java/application/");
        Config.publicDirectory = new File("src/main/java/application/public");
        Config.logfile = new File("src/main/java/application/views/logs");
        Config.username = "admin";
        Config.password = "hunter2";
        Config.fileController = "File";
        int portNumber = getPortNumber(args);
        try {
            ServerRunner runner = new ServerRunner(portNumber);
            runner.run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

