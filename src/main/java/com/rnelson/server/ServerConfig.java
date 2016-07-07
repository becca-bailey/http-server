package com.rnelson.server;

import com.rnelson.server.routing.Router;

import java.io.File;

public class ServerConfig {
    public static File rootDirectory;
    public static String packageName;
    public static Router router;
    public static String fileController = "File";
    public static File publicDirectory = new File("src/main/java/application/public");
    public static String routesClass;
}
