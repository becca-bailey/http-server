package com.rnelson.server;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerRunner runner = new ServerRunner(5000);
        runner.run();
    }
}

