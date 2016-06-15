package com.rnelson.server;

import java.util.*;

import static com.rnelson.server.Router.routeOptions;
import static com.rnelson.server.Router.statusCodesForRoutes;

public class Response {
    private String method;
    private String route;
    private String body = "";
    private String options = "";

    private static Map<Integer, String> statusCodes = new HashMap<Integer, String>();
    private static Map<String, List> requiredHeaderRows = new HashMap<String, List>();

    private String contentType = "Content-Type: text/plain";
    private String contentLength = "Content-Length: " + body.length();
    private String connection = "Connection: Keep-Alive";

    public Response(String method, String route) {
        this.method = method;
        this.route = route;

        Router router = new Router();
    }

    public void returnBody(String data) {
        this.body = data;
    }

    public static String status(Integer status) {
        statusCodes.put(200, "HTTP/1.1 200 OK");
        statusCodes.put(404, "HTTP/1.1 404 NOT FOUND");
        statusCodes.put(201, "HTTP/1.1 201 CREATED");

        return statusCodes.get(status);
    }

    private Boolean isValidRoute() {
        return routeOptions.containsKey(route) && routeOptions.get(route).contains(method);
    }

    public String getOptions() {
        String allowedOptions = String.join(",", routeOptions.get(route));
        return "Allow: " + allowedOptions;
    }

    public String getResponseStatus() {
        return statusCodesForRoutes.get(method);
    }

    private void populateRequiredHeaders() {
        List<String> standardRows = Arrays.asList(contentType, connection);
        List<String> optionsRows = Arrays.asList(contentType, options, connection, contentLength);
        List<String> postRows = Arrays.asList(contentType, connection, contentLength);

        requiredHeaderRows.put("GET", standardRows);
        requiredHeaderRows.put("HEAD", standardRows);
        requiredHeaderRows.put("OPTIONS", optionsRows);
        requiredHeaderRows.put("POST", standardRows);
        requiredHeaderRows.put("PUT", standardRows);
    }

    public String getHeaderAndBody() {
        StringBuilder header = new StringBuilder();
        if (isValidRoute()) {
            this.options = getOptions();
            populateRequiredHeaders();

            header.append(getResponseStatus());
            header.append("\r\n");
            header.append(String.join("\r\n", requiredHeaderRows.get(method)));
            header.append("\r\n\r\n");
            header.append(body);
        } else {
            header.append(status(404));
            header.append("\r\n\r\n");
        }

        return header.toString();
    }
}
