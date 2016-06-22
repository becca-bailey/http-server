package com.rnelson.server.response;

import com.rnelson.server.utilities.Router;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.*;

import static com.rnelson.server.utilities.Router.routeOptions;
import static com.rnelson.server.utilities.Router.statusCodesForRoutes;

public class ResponseHeaders {
    private String method;
    private String route;
    public String body = "";
    private String options = "";

    private List<String> arguments = new ArrayList<String>();

    public static Map<String, List> requiredHeaderRows = new HashMap<String, List>();
    private static Map<String, String> contentTypes = new HashMap<String, String>();

    private String contentType = "Content-Type: text/html";
    private String contentLength = "Content-Length: " + body.length();
    private String connection = "Connection: Keep-Alive";
    private String location = "Location: http://localhost:5000/";


    private Router router;

    public ResponseHeaders(String method, String route) {
        this.method = method;
        this.route = route;

        router = new Router();
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
        if (isRange()) {
            status = Response.status(206);
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

    public void sendArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public Boolean isRange() {
        return arguments.contains("range");
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
            header.append(Response.status(405));
            header.append("\r\n\r\n");
        } else {
            header.append(Response.status(404));
            header.append("\r\n\r\n");
        }
        return header.toString().getBytes();
    }

//    public byte[] getBody() {
//
//    }
//    public byte[] getBody() {
//        getPageContent();
//        byte[] echoContentBytes = emptyContent;
//        byte[] bodyContent = emptyContent;
//        if (route.equals("/echo")) {
//            echoContentBytes = body.getBytes();
//        }
//        if (method.equals("GET")) {
//            bodyContent = SharedUtilities.addByteArrays(echoContentBytes, getPageContent());
//        } else {
//            bodyContent = echoContentBytes;
//        }
//        return bodyContent;
//    }
}