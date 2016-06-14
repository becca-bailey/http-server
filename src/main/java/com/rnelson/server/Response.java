package com.rnelson.server;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private static Map<Integer, String> statusCodes = new HashMap<Integer, String>();

    public static String status(Integer status) {
        statusCodes.put(200, "HTTP/1.1 200 OK");

        return statusCodes.get(status);
    }
}
