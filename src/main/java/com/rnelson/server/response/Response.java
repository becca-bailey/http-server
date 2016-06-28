package com.rnelson.server.response;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private static Map<Integer, String> statusCodes = new HashMap<Integer, String>();
    private static String crlf = "\r\n";
    public static String notFound = status(404) + crlf + crlf;
    public static String methodNotAllowed = status(405) + crlf + crlf;

    private static String http(String responseStatus) {
        return "HTTP/1.1 " + responseStatus;
    }

    public static String status(Integer status) {
        statusCodes.put(200, http("200 OK"));
        statusCodes.put(201, http("201 Created"));
        statusCodes.put(204, http("204 No Content"));
        statusCodes.put(206, http("206 Partial Content"));
        statusCodes.put(302, http("302 Found"));
        statusCodes.put(404, http("404 Not Found"));
        statusCodes.put(405, http("405 Method Not Allowed"));
        statusCodes.put(418, http("418 I'm a teapot"));
        statusCodes.put(401, http("401 Not Authorized"));

        return statusCodes.get(status);
    }
}
