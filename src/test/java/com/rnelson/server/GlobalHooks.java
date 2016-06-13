package com.rnelson.server;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class GlobalHooks {
    private static boolean serverIsRunning = false;
    static ServerRunner serverRunner;
    static boolean afterAll = false;

    @Before
    public void startServer() {
        if (!serverIsRunning) {
            serverRunner = new ServerRunner(5000);
            Thread server = new Thread(serverRunner);
            server.start();
            serverIsRunning = true;
        }
    }
}
