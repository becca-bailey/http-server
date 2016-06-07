package com.rnelson.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    private final String request;
    private final String okayStatus = "HTTP/1.1 200 OK\r\n\r\n";
    private final String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
    private final String created = "HTTP/1.1 201 CREATED\r\n\r\n";

    private final List<String> routes = Arrays.asList("/", "/echo", "/form");

    public RequestHandler(String request) {
        this.request = request;
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

    private URL fullURL() throws MalformedURLException {
        String uriAndParameters = findMatch("\\/.*\\s", request);
        return new URL("http://example.com" + uriAndParameters);
    }

    public String method() {
        return findMatch("^\\S+", request);
    }

    public String uri() throws MalformedURLException {
        URL sampleURL = fullURL();
        return sampleURL.getPath();
    }

    public String queryString() throws MalformedURLException {
        String queryString = fullURL().getQuery();
        return findMatch("[a-z]*$", queryString);
    }

    public String POSTResponse() throws MalformedURLException {
        String uri = uri();
        if (uri.equals("/echo")) {
            return okayStatus + queryString();
        } else {
            return created;
        }
    }

    public String getResponse() throws MalformedURLException {
        String response;
        if (routes.contains(uri())) {
            response = getResponseForValidRoute();
        } else {
            response = notFound;
        }
        return response;
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
}
