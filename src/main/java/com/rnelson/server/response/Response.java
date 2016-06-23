package com.rnelson.server.response;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private static Map<Integer, String> statusCodes = new HashMap<Integer, String>();

    public static String status(Integer status) {
        statusCodes.put(200, "HTTP/1.1 200 OK");
        statusCodes.put(404, "HTTP/1.1 404 NOT FOUND");
        statusCodes.put(201, "HTTP/1.1 201 CREATED");
        statusCodes.put(418, "HTTP/1.1 418 I'm a teapot");
        statusCodes.put(302, "HTTP/1.1 302 Found");
        statusCodes.put(405, "HTTP/1.1 405 Method Not Allowed");
        statusCodes.put(206, "HTTP/1.1 206 Partial Content");

        return statusCodes.get(status);
    }
}
