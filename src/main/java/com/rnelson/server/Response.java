package com.rnelson.server;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private static Map<Integer, String> statusCodes = new HashMap<Integer, String>();
    public String body;
    public String statusCode;

    public static String status(Integer status) {
        statusCodes.put(200, "HTTP/1.1 200 OK");
        statusCodes.put(404, "HTTP/1.1 404 NOT FOUND");
        statusCodes.put(201, "HTTP/1.1 201 CREATED");

        return statusCodes.get(status);
    }
}
