package com.rnelson.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String method;
    private String uri;
    private String body;
    private static Map<String, List> routes = new HashMap<String, List>();
    private static Map<String, String> statusCodes = new HashMap<String, String>();

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;

        routes.put("/", Arrays.asList("GET", "HEAD"));
        routes.put("/echo", Arrays.asList("GET", "POST"));
        routes.put("/form", Arrays.asList("POST"));

        statusCodes.put("GET", Response.status(200));
        statusCodes.put("HEAD", Response.status(200));
        statusCodes.put("POST", Response.status(201));
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

    public String getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusCode());
        response.append("\r\n\r\n");
        if (uri.equals("/echo")) {
            response.append(body);
        }
        return response.toString();
    }

    public void sendBody(String data) {
        this.body = data;
    }
}
