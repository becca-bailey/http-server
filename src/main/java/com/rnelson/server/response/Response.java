package com.rnelson.server.response;

import com.rnelson.server.utilities.Router;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.*;

import static com.rnelson.server.utilities.Router.pageContent;
import static com.rnelson.server.utilities.Router.routeOptions;
import static com.rnelson.server.utilities.Router.statusCodesForRoutes;

public class Response {
    private String method;
    private String route;
    private String body = "";
    private String options = "";

    private static Map<Integer, String> statusCodes = new HashMap<Integer, String>();
    public static Map<String, List> requiredHeaderRows = new HashMap<String, List>();
    private static Map<String, String> contentTypes = new HashMap<String, String>();

    private String contentType = "Content-Type: text/html";
    private String contentLength = "Content-Length: " + body.length();
    private String connection = "Connection: Keep-Alive";
    private String location = "Location: http://localhost:5000/";
    private byte[] emptyContent = new byte[0];


    private Router router;

    public Response(String method, String route) {
        this.method = method;
        this.route = route;

        router = new Router();

        if (method.equals("DELETE")) {
            deletePageContent();
        }
    }

    private void deletePageContent() {
        pageContent.put(route, emptyContent);
    }

    public Boolean echoesBody() {
        return (method.equals("POST") || method.equals("PUT"));
    }

    public void sendRequestBody(String data) {
        this.body = data;
        pageContent.put(route, body.getBytes());
    }

    public static String status(Integer status) {
        statusCodes.put(200, "HTTP/1.1 200 OK");
        statusCodes.put(404, "HTTP/1.1 404 NOT FOUND");
        statusCodes.put(201, "HTTP/1.1 201 CREATED");
        statusCodes.put(418, "HTTP/1.1 418 I'm a teapot");
        statusCodes.put(302, "HTTP/1.1 302 Found");
        statusCodes.put(405, "HTTP/1.1 405 Method Not Allowed");

        return statusCodes.get(status);
    }

    private Boolean isValidRoute() {
        return routeOptions.containsKey(route) && routeOptions.get(route).contains(method);
    }

    private Boolean validRouteAndInvalidMethod() {
        return routeOptions.containsKey(route) && !(routeOptions.get(route).contains(method));
    }

    public String getOptions() {
        String allowedOptions = String.join(",", routeOptions.get(route));
        return "Allow: " + allowedOptions;
    }

    public String responseStatus() {
        String status = statusCodesForRoutes.get(method + " " + route);
        if (status == null) {
            status = statusCodesForRoutes.get(method + " *");
        }
        return status;
    }

    private void populateRequiredHeaders() {
        List<String> standardRows = Arrays.asList(contentType);
        List<String> optionsRows = Arrays.asList(contentType, options, contentLength);
        List<String> typeAndLength = Arrays.asList(contentType, contentLength);
        List<String> redirectRows = Arrays.asList(location);

        requiredHeaderRows.put("GET *", standardRows);
        requiredHeaderRows.put("HEAD *", standardRows);
        requiredHeaderRows.put("OPTIONS *", optionsRows);
        requiredHeaderRows.put("POST *", standardRows);
        requiredHeaderRows.put("PUT *", standardRows);
        requiredHeaderRows.put("DELETE *", standardRows);
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

    public byte[] getHeader() {
        StringBuilder header = new StringBuilder();
        if (isValidRoute()) {
            this.options = getOptions();
            populateRequiredHeaders();

            header.append(responseStatus());
            header.append("\r\n");
            header.append(String.join("\r\n", getRequiredHeaderRows()));
            header.append("\r\n\r\n");
        } else if (validRouteAndInvalidMethod()) {
            header.append(status(405));
            header.append("\r\n\r\n");
        } else {
            header.append(status(404));
            header.append("\r\n\r\n");
        }
        return header.toString().getBytes();
    }

    public byte[] getPageContent() {
        byte[] content = emptyContent;
        if (isValidRoute() && pageContent.get(route) != null) {
            content = pageContent.get(route);
        }
        return content;
    }

    public byte[] getBody() {
        byte[] echoContentBytes = emptyContent;
        byte[] bodyContent = emptyContent;
        if (route.equals("/echo")) {
            echoContentBytes = body.getBytes();
        }
        if (method.equals("GET")) {
            bodyContent = SharedUtilities.addByteArrays(echoContentBytes, getPageContent());
        } else {
            bodyContent = echoContentBytes;
        }
        return bodyContent;
    }
}