package com.rnelson.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String method;
    private String uri;
    private String body;
    private static Map<String, List<String>> routes = new HashMap<String, List<String>>();
    private static Map<String, String> statusCodes = new HashMap<String, String>();

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;

        routes.put("/", Arrays.asList("GET", "HEAD"));
        routes.put("/echo", Arrays.asList("GET", "POST"));
        routes.put("/form", Arrays.asList("POST"));
        routes.put("/method_options", Arrays.asList("GET", "HEAD", "POST", "OPTIONS", "PUT"));
        routes.put("/method_options2", Arrays.asList("GET", "OPTIONS"));

        statusCodes.put("GET", Response.status(200));
        statusCodes.put("HEAD", Response.status(200));
        statusCodes.put("POST", Response.status(200));
        statusCodes.put("OPTIONS", Response.status(200));
    }

    private Boolean isValid() {
        return routes.containsKey(uri) && routes.get(uri).contains(method);
    }

    private String getStatusCode() {
        if (isValid()) {
            return statusCodes.get(method);
        } else {
            return Response.status(404);
        }
    }

    private String getOptions() {
        String routeOptions = String.join(",", routes.get(uri));
        return "Allow: " + routeOptions;
    }

    public String getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusCode());
        if (method.equals("OPTIONS")) {
            response.append("\r\n");
            response.append(getOptions());
        }
        response.append("\r\nContent-Length: 0");
//        response.append(body.length());
        response.append("\r\nContent-Type: text/html");
        response.append("\r\nConnection: closed");
        response.append("\r\n\r\n");
        if (uri.equals("/echo") && method.equals("/post")) {
            response.append(body);
        }
        System.out.println(response.toString());
        return response.toString();
    }

    public void sendBody(String data) {
        this.body = data;
    }
}
