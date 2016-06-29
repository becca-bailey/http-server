package com.rnelson.server.utilities;

import com.rnelson.server.Response;

import java.util.*;

public class RouterList {
    public static Map<String, List<String>> routeOptions = new HashMap<String, List<String>>();
    public static Map<String, String> statusCodesForRequests = new HashMap<String, String>();
    public static Map<String, String> authorizedUsers = new HashMap<String, String>();
    public static List<String> protectedRoutes = new ArrayList<String>();


    public RouterList() {
        routeOptions.put("/", Arrays.asList("GET", "HEAD"));
        routeOptions.put("/echo", Arrays.asList("GET", "POST"));
        routeOptions.put("/form", Arrays.asList("POST", "PUT", "GET", "DELETE"));
        routeOptions.put("/method_options", Arrays.asList("GET", "HEAD", "POST", "OPTIONS", "PUT"));
        routeOptions.put("/method_options2", Arrays.asList("GET", "OPTIONS"));
        routeOptions.put("/coffee", Arrays.asList("GET"));
        routeOptions.put("/tea", Arrays.asList("GET"));
        routeOptions.put("/redirect", Arrays.asList("GET"));
        routeOptions.put("/parameters", Arrays.asList("GET"));
        routeOptions.put("/logs", Arrays.asList("GET", "HEAD", "POST"));

        statusCodesForRequests.put("GET *", Response.status(200));
        statusCodesForRequests.put("HEAD *", Response.status(200));
        statusCodesForRequests.put("POST *", Response.status(200));
        statusCodesForRequests.put("OPTIONS *", Response.status(200));
        statusCodesForRequests.put("PUT *", Response.status(200));
        statusCodesForRequests.put("DELETE *", Response.status(200));
        statusCodesForRequests.put("PATCH *", Response.status(204));
        statusCodesForRequests.put("GET /coffee", Response.status(418));
        statusCodesForRequests.put("GET /redirect", Response.status(302));
        statusCodesForRequests.put("GET /form", Response.status(200));
        statusCodesForRequests.put("GET /parameters", Response.status(200));
        statusCodesForRequests.put("GET /logs", Response.status(401));

        protectedRoutes.add("/logs");

        authorizedUsers.put("admin", "hunter2");
    }
}
