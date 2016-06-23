package com.rnelson.server.response;

import com.rnelson.server.utilities.Router;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.*;

import static com.rnelson.server.utilities.Router.routeOptions;
import static com.rnelson.server.utilities.Router.statusCodesForRoutes;

public class ResponseHeaders {
    private String method;
    private String route;
    private String options = "";
    private Integer bodyLength;

    private List<String> arguments = new ArrayList<String>();

    public static Map<String, List> requiredHeaderRows = new HashMap<String, List>();
    private static Map<String, String> contentTypes = new HashMap<String, String>();

    private String contentType = "Content-Type: text/html";
    private String location = "Location: http://localhost:5000/";

    private Router router;

    public ResponseHeaders(String method, String route) {
        this.method = method;
        this.route = route;

        router = new Router();
    }

    public void sendBodyLength(Integer length) {
        this.bodyLength = length;
    }

    private Boolean isValidRoute() {
        return Router.routeOptions.containsKey(route) && routeOptions.get(route).contains(method);
    }

    private Boolean validRouteAndInvalidMethod() {
        return Router.routeOptions.containsKey(route) && !(routeOptions.get(route).contains(method));
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

    private String getContentLength() {
        return "Content-Length: " + bodyLength;
    }

    private void populateRequiredHeaders() {
        String contentLength = getContentLength();

        List<String> standardRows = Arrays.asList(contentType);
        List<String> optionsRows = Arrays.asList(contentType, options);
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

    private Boolean isRange() {
        return arguments.contains("range");
    }

    private Boolean bodyHasContent() {
        return !arguments.contains("headOnly");
    }

    public byte[] getHeader() {
        StringBuilder header = new StringBuilder();
        if (isValidRoute()) {
            this.options = getOptions();
            populateRequiredHeaders();

            header.append(responseStatus());
            header.append("\r\n");
            header.append(String.join("\r\n", getRequiredHeaderRows()));
            if (bodyHasContent()) {
                header.append("\r\n");
                header.append(getContentLength());
            }
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
}