package com.rnelson.server;

import com.rnelson.server.utilities.exceptions.ServerException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServerArguments {
    private final String[] args;
    private final Map<String,String> arguments;

    public ServerArguments(String[] args) {
        this.args = args;
        arguments = new HashMap<>();
        try {
            addArgumentsToMap();
        } catch (ServerException e) {
            System.err.println(e.getMessage());
        }
    }

    private Boolean isPortParameter(String parameter) {
        return (parameter.equals("-p"));
    }

    private Boolean isRootParameter(String parameter) {
        return (parameter.equals("-r"));
    }

    private void addArgumentsToMap() throws ServerException {
        try {
            for (int i = 0; i < args.length; i++) {
                if (isPortParameter(args[i])) {
                    arguments.put("port", args[i + 1]);
                }
                if (isRootParameter(args[i])) {
                    arguments.put("rootDirectory", args[i + 1]);
                }
            }
        } catch (Exception e) {
            throw new ServerException("Usage: java -jar package/http-server-0.0.1.jar -p <port number> -r <root directory>");
        }
    }

    public int getPortNumber() {
        int portNumber = 5000;
        try {
            portNumber = Integer.parseInt(arguments.get("port"));
        } catch (Exception e) {
            // returns default port
        }
        return portNumber;
    }

    public File getRootDirectory() throws ServerException {
        try {
            String rootPath = arguments.get("rootDirectory");
            return new File(rootPath);
        } catch (NullPointerException e) {
            throw new ServerException("Usage: java -jar package/http-server-0.0.1.jar -p <port number> -r <root directory>");
        } catch (Exception f) {
            throw new ServerException("Directory " + arguments.get("rootDirectory") + " not found.");
        }
    }
}