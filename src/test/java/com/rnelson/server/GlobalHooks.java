package com.rnelson.server;

import cucumber.api.java.Before;

import java.io.File;

public class GlobalHooks {
    private static boolean serverIsRunning = false;
    static ServerRunner serverRunner;

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
                File rootDirectory = new File("src/main/java/application");
                serverRunner = new ServerRunner(5000, rootDirectory);
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
