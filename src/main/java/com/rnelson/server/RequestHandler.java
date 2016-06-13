package com.rnelson.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    private final String[] request;
    private final String okayStatus = "HTTP/1.1 200 OK\r\n";
    private final String notFound = "HTTP/1.1 404 NOT FOUND\r\n";
    private final String created = "HTTP/1.1 201 CREATED\r\n";

    private final List<String> routes = Arrays.asList("/", "/echo", "/form");

    public RequestHandler(String request) {
        this.request = request.split("\n");
    }

    private String findMatch(String regex, String request) {
        String match = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            match = matcher.group().trim();
        }
        return match;
    }

    public String getRequestBody() {
        StringBuilder requestBody = new StringBuilder();
        Integer firstLineOfBody;
        try {
            firstLineOfBody = request.length + Arrays.asList(request).indexOf("\n");
        } catch (Exception e) {
            firstLineOfBody = 10000;
        }
        for (int i = 0; i < request.length; i++) {
            if (i >= firstLineOfBody) {
                requestBody.append(request[i]);
                requestBody.append("\n");
            }
        }
        return requestBody.toString();
    }

    private URL fullURL() throws MalformedURLException {
        String uriAndParameters = findMatch("\\/.*\\s", request[0]);
        return new URL("http://example.com" + uriAndParameters);
    }

    public String method() {
        return findMatch("^\\S+", request[0]);
    }

    public String uri() throws MalformedURLException {
        URL sampleURL = fullURL();
        return sampleURL.getPath();
    }

    public String queryString() throws MalformedURLException {
        String queryString = fullURL().getQuery();
        return findMatch("[a-z]*$", queryString);
    }

    public String getResponse() throws MalformedURLException {
        String response;
        if (routes.contains(uri())) {
            response = getResponseForValidRoute();
        } else {
            response = notFound;
        }
        return response + "\r\n";
    }

    private String getResponseForValidRoute() throws MalformedURLException {
        String method = method();
        String response;
        if (method.equals("POST")) {
            response = POSTResponse();
        } else {
            response = okayStatus;
        }
        return response;
    }

    public String POSTResponse() throws MalformedURLException {
        String response;
        String body = getRequestBody();
        String contentLength = "Content-Length: " + Integer.toString(body.length());
        String uri = uri();
        if (uri.equals("/echo")) {
            response = created +
                    contentLength + "\r\n\r\n" +
                    body;
        } else {
            response = created;
        }
        return response;
    }
}
