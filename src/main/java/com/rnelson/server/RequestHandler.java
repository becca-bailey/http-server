package com.rnelson.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    private final String[] requestLines;

    private final List<String> routes = Arrays.asList("/", "/echo", "/form");

    public RequestHandler(String request) {
        this.requestLines = request.split("\n");
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
        String uriAndParameters = findMatch("\\/.*\\s", requestLines[0]);
        return new URL("http://example.com" + uriAndParameters);
    }

    public String method() {
        return findMatch("^\\S+", requestLines[0]);
    }

    public String uri() throws MalformedURLException {
        URL sampleURL = fullURL();
        return sampleURL.getPath();
    }

    public String processRequest() throws MalformedURLException {
        String method = method();
        String uri = uri();
        Request request = new Request(method, uri);
        request.sendBody(getRequestBody());
        return request.getResponse();
    }
}
