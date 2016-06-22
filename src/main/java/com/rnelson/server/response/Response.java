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