package com.rnelson.server;

import java.util.*;

import static com.rnelson.server.Router.pageContent;
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
    private String location = "Location: http://localhost:5000/";

    public Response(String method, String route) {
        this.method = method;
        this.route = route;

        Router router = new Router();
    }

    public void sendBody(String data) {
        this.body = data;
    }

    public static String status(Integer status) {
        statusCodes.put(200, "HTTP/1.1 200 OK");
        statusCodes.put(404, "HTTP/1.1 404 NOT FOUND");
        statusCodes.put(201, "HTTP/1.1 201 CREATED");
        statusCodes.put(418, "HTTP/1.1 418 I'm a teapot");
        statusCodes.put(302, "HTTP/1.1 302 Found");

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
        String status = statusCodesForRoutes.get(method + " " + route);
        if (status == null) {
            status = statusCodesForRoutes.get(method + " *");
        }
        return status;
    }

    private void populateRequiredHeaders() {
        List<String> standardRows = Arrays.asList(contentType, connection);
        List<String> optionsRows = Arrays.asList(contentType, options, connection, contentLength);
        List<String> postRows = Arrays.asList(contentType, connection, contentLength);
        List<String> redirectRows = Arrays.asList(location);

        requiredHeaderRows.put("GET *", standardRows);
        requiredHeaderRows.put("HEAD *", standardRows);
        requiredHeaderRows.put("OPTIONS *", optionsRows);
        requiredHeaderRows.put("POST *", standardRows);
        requiredHeaderRows.put("PUT *", standardRows);
        requiredHeaderRows.put("GET /redirect", redirectRows);
    }

    private List<String> getRequiredHeaderRows() {
        List<String> rows;
        rows = requiredHeaderRows.get(method + " " + route);
        if (rows == null) {
            rows = requiredHeaderRows.get(method + " *");
        }
        return rows;
    }

    public String getHeaderAndBody() {
        StringBuilder response = new StringBuilder();
        if (isValidRoute()) {
            this.options = getOptions();
            populateRequiredHeaders();

            response.append(getResponseStatus());
            response.append("\r\n");
            response.append(String.join("\r\n", getRequiredHeaderRows()));
            response.append("\r\n\r\n");
            response.append(body);

            String content = pageContent.get(route);
            if (content != null) {
                response.append(content);
            }
        } else {
            response.append(status(404));
            response.append("\r\n\r\n");
        }
        return response.toString();
    }
}
