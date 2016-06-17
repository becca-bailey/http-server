package com.rnelson.server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    public static Map<String, List<String>> routeOptions = new HashMap<String, List<String>>();
    public static Map<String, String> statusCodesForRoutes = new HashMap<String, String>();
    public static Map<String, String> pageContent = new HashMap<String, String>();
    private String publicDirectory = "public";


    public Router() {
        routeOptions.put("/", Arrays.asList("GET", "HEAD"));
        routeOptions.put("/echo", Arrays.asList("GET", "POST"));
        routeOptions.put("/form", Arrays.asList("POST", "PUT", "GET", "DELETE"));
        routeOptions.put("/method_options", Arrays.asList("GET", "HEAD", "POST", "OPTIONS", "PUT"));
        routeOptions.put("/method_options2", Arrays.asList("GET", "OPTIONS"));
        routeOptions.put("/coffee", Arrays.asList("GET"));
        routeOptions.put("/tea", Arrays.asList("GET"));
        routeOptions.put("/redirect", Arrays.asList("GET"));

        statusCodesForRoutes.put("GET *", Response.status(200));
        statusCodesForRoutes.put("HEAD *", Response.status(200));
        statusCodesForRoutes.put("POST *", Response.status(200));
        statusCodesForRoutes.put("OPTIONS *", Response.status(200));
        statusCodesForRoutes.put("PUT *", Response.status(200));
        statusCodesForRoutes.put("DELETE *", Response.status(200));
        statusCodesForRoutes.put("GET /coffee", Response.status(418));
        statusCodesForRoutes.put("GET /redirect", Response.status(302));
        statusCodesForRoutes.put("GET /form", Response.status(200));

        pageContent.put("/coffee", "I'm a teapot");
        pageContent.put("/", getDirectoryLinks());

        handleAllFiles();
    }

    private void handleAllFiles() {
        for (File file : getDirectoryListing()) {
            Router.statusCodesForRoutes.put("GET /" + file.getName(), Response.status(200));
            Router.routeOptions.put("/" + file.getName(), Arrays.asList("GET"));

            FileHandler handler = new FileHandler(file);
            try {
                Router.pageContent.put("/" + file.getName(), handler.getFileContents());
                Response.requiredHeaderRows.put("GET /" + file.getName(), Arrays.asList(handler.fileContentTypeHeader()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    String getDirectoryLinks() {
        File[] directoryListing = getDirectoryListing();

        StringBuilder directoryContents = new StringBuilder();
        directoryContents.append(publicDirectory);

        for (File file : directoryListing) {
            FileHandler fileHandler = new FileHandler(file);
            directoryContents.append(fileHandler.generateFileLink());
        }
        return directoryContents.toString();
    }

    private File[] getDirectoryListing() {
        File directory = new File(publicDirectory);
        return directory.listFiles();
    }
}
