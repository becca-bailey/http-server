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

    private final List<String> routes = Arrays.asList("/", "/echo", "/form");

    public RequestHandler(String request) {
        this.requestLines = request.split("\n");
        method = method();
        route = route();
    }

    public byte[] processRequest() {
        Request request = new Request(method, route);
        request.sendBody(getRequestBody());
        return request.getResponse();
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

    private URL fullURL() {
        String uriAndParameters = SharedUtilities.findMatch("\\/.*\\s", requestLines[0], 0);
        URL fullURL = null;
        try {
            fullURL = new URL("http://example.com" + uriAndParameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return fullURL;
    }

    public String method() {
        return SharedUtilities.findMatch("^\\S+", requestLines[0], 0);
    }

    public String route() {
        URL sampleURL = fullURL();
        return sampleURL.getPath();
    }

    public Boolean requestIsImage() {
        for (String extension : SharedUtilities.imageExtensions) {
            if (route.contains(extension)) {
                return true;
            }
        }
        return false;
    }
}
