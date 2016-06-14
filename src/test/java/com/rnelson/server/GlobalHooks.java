package com.rnelson.server;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class GlobalHooks {
    private static boolean serverIsRunning = false;
    static ServerRunner serverRunner;
    public static Integer counter = 1;

    @Before
    public void startServer() {
        if (!serverIsRunning) {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    serverRunner.stop();
                    serverIsRunning = false;
                }
            });
            try {
                serverRunner = new ServerRunner(5000);
                Thread server = new Thread(serverRunner);
                server.start();
                serverIsRunning = true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
