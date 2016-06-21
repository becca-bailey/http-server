package com.rnelson.server.request;

import com.rnelson.server.utilities.SharedUtilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RequestHandler {
    private final String[] requestLines;
    private String method;
    private String route;

    public RequestHandler(String request) {
        this.requestLines = request.split("\n");
        this.method = method();
        this.route = route();
    }

    public String method() {
        return SharedUtilities.findMatch("^\\S+", requestLines[0], 0);
    }

    public String route() {
        return SharedUtilities.findMatch("\\/([a-z]|[.|_|-]|\\d)*", requestLines[0], 0);
    }

    public String getRequestBody() {
        //refactor this later
        StringBuilder requestBody = new StringBuilder();
        Integer firstLineOfBody;
        try {
            firstLineOfBody = requestLines.length + Arrays.asList(requestLines).indexOf("\n");
        } catch (Exception e) {
            firstLineOfBody = 10000;
        }
        for (int i = 0; i < requestLines.length; i++) {
            if (i >= firstLineOfBody) {
                requestBody.append(requestLines[i]);
                requestBody.append("\n");
            }
        }
        return requestBody.toString().trim();
    }

    public String parameters() {
        return SharedUtilities.findMatch("([?])(.*)", requestLines[0], 2);
    }

    public byte[] processRequest() {
        Request request = new Request(method, route);
        request.sendBody(getRequestBody());
        return request.getResponse();
    }
}
