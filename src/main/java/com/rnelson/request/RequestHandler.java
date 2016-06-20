package com.rnelson.request;

import com.rnelson.utilities.SharedUtilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RequestHandler {
    private final String[] requestLines;

    private final List<String> routes = Arrays.asList("/", "/echo", "/form");

    public RequestHandler(String request) {
        this.requestLines = request.split("\n");
    }

    public String getRequestBody() {
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

    private URL fullURL() throws MalformedURLException {
        String uriAndParameters = SharedUtilities.findMatch("\\/.*\\s", requestLines[0], 0);
        return new URL("http://example.com" + uriAndParameters);
    }

    public String method() {
        return SharedUtilities.findMatch("^\\S+", requestLines[0], 0);
    }

    public String route() throws MalformedURLException {
        URL sampleURL = fullURL();
        return sampleURL.getPath();
    }

    public String processRequest() throws MalformedURLException {
        String method = method();
        String route = route();
        Request request = new Request(method, route);
        request.sendBody(getRequestBody());
        return request.getResponse();
    }
}
