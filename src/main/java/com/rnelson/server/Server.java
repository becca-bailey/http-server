package com.rnelson.server;

import java.io.File;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerArguments arguments = new ServerArguments(args);
        int portNumber = arguments.getPortNumber();
        File rootDirectory = arguments.getRootDirectory();
        try {
            ServerRunner runner = new ServerRunner(portNumber, rootDirectory);
            runner.run();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

