package com.rnelson.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    private String request;
    private List<String> routes = Arrays.asList("/", "/echo");

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
        return fullURL().getPath();
    }

    public String queryString() throws MalformedURLException {
        String queryString = fullURL().getQuery();
        return findMatch("[a-z]*$", queryString);
    }

    public String getEchoResponse() throws MalformedURLException {
        return (method().equals("POST")) ? queryString() : "";
    }

    public String getResponse() throws MalformedURLException {
        String okayStatus = "HTTP/1.1 200 OK\r\n\r\n";
        String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
        String response = routes.contains(uri()) ? okayStatus : notFound;
        if (method().equals("POST")) {
            response += getEchoResponse();
        }
        return response;
    }
}
