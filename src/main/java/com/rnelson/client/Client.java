package com.rnelson.client;

public class Client {

    public static void main(String[] args) throws Exception {
        ClientRunner runner = new ClientRunner("localhost", 5000);
        runner.run();
    }
}