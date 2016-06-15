package com.rnelson.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    public static Map<String, List<String>> routeOptions = new HashMap<String, List<String>>();
    public static Map<String, String> statusCodesForRoutes = new HashMap<String, String>();


    public Router() {
        routeOptions.put("/", Arrays.asList("GET", "HEAD"));
        routeOptions.put("/echo", Arrays.asList("GET", "POST"));
        routeOptions.put("/form", Arrays.asList("POST", "PUT"));
        routeOptions.put("/method_options", Arrays.asList("GET", "HEAD", "POST", "OPTIONS", "PUT"));
        routeOptions.put("/method_options2", Arrays.asList("GET", "OPTIONS"));

        statusCodesForRoutes.put("GET", Response.status(200));
        statusCodesForRoutes.put("HEAD", Response.status(200));
        statusCodesForRoutes.put("POST", Response.status(200));
        statusCodesForRoutes.put("OPTIONS", Response.status(200));
        statusCodesForRoutes.put("PUT", Response.status(200));
    }
}
