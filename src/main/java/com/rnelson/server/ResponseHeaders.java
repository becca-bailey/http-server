package com.rnelson.server;

import com.rnelson.server.Response;
import com.rnelson.server.utilities.RouterList;

import java.util.*;

import static com.rnelson.server.utilities.RouterList.routeOptions;

public class ResponseHeaders {
    private final String method;
    private final String route;
    private String options;
    private Integer bodyLength;

    private List<String> arguments = new ArrayList<>();

    public static Map<String, List<String>> requiredHeaderRows = new HashMap<>();
    private static Map<String, String> contentTypes = new HashMap<>();

    private String fourOhFour = Response.status(404) + "\r\n\r\n";
    private String fourOhFive = Response.status(405) + "\r\n\r\n";

    public ResponseHeaders(String method, String route) {
        this.method = method;
        this.route = route;

        RouterList routerList = new RouterList();
    }

    public void sendBodyLength(Integer length) {
        this.bodyLength = length;
    }

    public void sendArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public byte[] getHeader() {
        if (isValidRoute()) {
            return headerForValidRoute().getBytes();
        } else if (methodNotAllowed()) {
            return fourOhFive.getBytes();
        } else {
            return fourOhFour.getBytes();
        }
    }

    private Boolean isValidRoute() {
        return RouterList.routeOptions.containsKey(route) && validMethod();
    }

    private Boolean methodNotAllowed() {
        return RouterList.routeOptions.containsKey(route) && !validMethod();
    }

    private String headerForValidRoute() {
        StringBuilder header = new StringBuilder();
        this.options = getOptions();
        populateRequiredHeaders();
        header.append(responseStatus());
        header.append("\r\n");
        header.append(getRequiredHeaderRows());
        if (headerIncludesSpecialRows()) {
            header.append("\r\n");
            header.append(getSpecialHeaderRows());
        }
        header.append("\r\n\r\n");
        return header.toString();
    }

    private Boolean headerIncludesSpecialRows() {
        return unauthorized() || bodyHasContent();
    }

    private String getSpecialHeaderRows() {
        String authorization = "WWW-Authenticate: Basic";

        List<String> rows = new ArrayList<>();
        if (unauthorized()) {
            rows.add(authorization);
        }
        if (bodyHasContent()) {
            rows.add(getContentLength());
        }
        return String.join("\r\n", rows);
    }

    public String responseStatus() {
        if (responseStatusForArgument()) {
            return getStatusFromArgument();
        } else {
            return getStatusFromRoute();
        }
    }

    private String getStatusFromArgument() {
        if (isRange()) {
            return Response.status(206);
        }
        if (isAuthorized()) {
            return Response.status(200);
        }
        return "";
    }

    private String getStatusFromRoute() {
        String status = RouterList.statusCodesForRequests.get(method + " " + route);
        if (status == null) {
            status = RouterList.statusCodesForRequests.get(method + " *");
        }
        return status;
    }

    private Boolean validMethod() {
        return routeOptions.get(route).contains(method);
    }

    private String getRequiredHeaderRows() {
        List rows = requiredHeaderRows.get(method + " " + route);
        if (rows == null) {
            rows = requiredHeaderRows.get(method + " *");
        }
        return String.join("\r\n", rows);
    }

    public String getOptions() {
        String allowedOptions = String.join(",", routeOptions.get(route));
        return "Allow: " + allowedOptions;
    }

    private boolean responseStatusForArgument() {
        return isRange() || isAuthorized();
    }

    private boolean isAuthorized() {
        return arguments.contains("authorized");
    }

    private boolean unauthorized() {
        return arguments.contains("unauthorized");
    }

    private String getContentLength() {
        return "Content-Length: " + bodyLength;
    }

    private void populateRequiredHeaders() {
        String defaultContentType = "Content-Type: text/html";
        String location = "Location: http://localhost:5000/";

        String contentLength = getContentLength();

        List<String> standardRows = Arrays.asList(defaultContentType);
        List<String> optionsRows = Arrays.asList(defaultContentType, options);
        List<String> redirectRows = Arrays.asList(location);

        requiredHeaderRows.put("GET *", standardRows);
        requiredHeaderRows.put("HEAD *", standardRows);
        requiredHeaderRows.put("OPTIONS *", optionsRows);
        requiredHeaderRows.put("POST *", standardRows);
        requiredHeaderRows.put("PUT *", standardRows);
        requiredHeaderRows.put("DELETE *", standardRows);
        requiredHeaderRows.put("PATCH *", standardRows);
        requiredHeaderRows.put("GET /redirect", redirectRows);
    }

    private Boolean isRange() {
        return arguments.contains("range");
    }

    private Boolean bodyHasContent() {
        return !arguments.contains("headOnly");
    }

}