package com.rnelson.server;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    public static Map<String, List<String>> routeOptions = new HashMap<String, List<String>>();
    public static Map<String, String> statusCodesForRoutes = new HashMap<String, String>();
    public static Map<String, String> pageContent = new HashMap<String, String>();


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
        pageContent.put("/", getDirectoryContents());
    }

    private String generateFileLink(File file) {
        return "<a href=\"/" + file.getName() + "\">" + file.getName() + "</a>";
    }

    private String getDirectoryContents() {
        StringBuilder directoryContents = new StringBuilder();
        directoryContents.append("public");
        File directory = new File("public");
        File[] directoryListing = directory.listFiles();
        for (File file : directoryListing) {
            directoryContents.append(generateFileLink(file));
        }
        return directoryContents.toString();
    }
}
