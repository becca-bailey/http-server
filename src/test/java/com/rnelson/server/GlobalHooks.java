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
            try {
                serverRunner = new ServerRunner(5000);
                Thread server = new Thread(serverRunner);
                Thread.sleep(5000);
                server.start();
                serverIsRunning = true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
