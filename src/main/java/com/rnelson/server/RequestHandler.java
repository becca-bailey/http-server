package com.rnelson.server;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    String request;
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

    public String method() {
        return findMatch("^\\S+", request);
    }

    public String parameter() {
        return findMatch("\\/[a-z]*\\s", request);
    }

    public String getResponse() {
        String response = "";
        String okayStatus = "HTTP/1.1 200 OK\r\n\r\n";
        if (routes.contains(parameter())) {
            response += okayStatus;
        }
        if (parameter().equals("/echo")) {
            response = okayStatus + request;
        }
        return response;
    }
}
