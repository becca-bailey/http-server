package com.rnelson.server.request;

import com.rnelson.server.response.BodyContent;
import com.rnelson.server.response.ResponseHeaders;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandler {
    private String method;
    private String route;
    private String body;
    private String request;
    private String[] headerLines;
    private String[] responseParameters;
    public Map<String, String> headerFields;


    public RequestHandler(String request) {
        this.request = request;
        this.headerLines = splitHeader();
        this.method = method();
        this.route = route();
        this.body = getRequestBody();
    }

    private String statusLine() {
        String[] headerLines = splitHeader();
        return headerLines[0];
    }

    public String headerOnly() {
        return SharedUtilities.findMatch("(.*)([\\r]*\\n[\\r]*\\n)", request, 1);
    }

    public String[] splitHeader() {
        String header = headerOnly();
        String[] headerLines;
        try {
            headerLines = header.split("\\n");
        } catch (NullPointerException e) {
            headerLines = new String[] {header};
        }
        return headerLines;
    }

    public String method() {
        return SharedUtilities.findMatch("^\\S+", headerLines[0], 0);
    }

    public String route() {
        return SharedUtilities.findMatch("\\/([a-z]|[.|_|-]|\\d)*", headerLines[0], 0);
    }

    public String getRequestBody() {
        return SharedUtilities.findMatch("([\\r]*\\n[\\r]*\\n)(.*)", request, 2);
    }

    public String parameters() {
        return SharedUtilities.findMatch("([?])(.*)", headerLines[0], 2);
    }

    public Map<String, String> parseHeaders() {
        headerFields = new HashMap<String, String>();
        for (int i = 1; i < headerLines.length; i++) {
            String[] fields = headerLines[i].split(":");
            String parameter = fields[0];
            String options = fields[1].trim();
            headerFields.put(parameter, options);
        }
        return headerFields;
    }

    private List<String> getResponseBodyArguments() {
        List<String> arguments = new ArrayList<String>();

        Boolean echoResponseBody = route.equals("/echo") && ((method.equals("POST") || method.equals("PUT")));
        Boolean headOnly = method.equals("HEAD") || method.equals("POST") || method.equals("PUT");
        Boolean delete = method.equals("DELETE");
        Boolean range = parseHeaders().containsKey("Content-Range");
        // add has authorization

        Map<String, Boolean> argumentAndConditions = new HashMap<String, Boolean>();
        argumentAndConditions.put("echoResponseBody", echoResponseBody);
        argumentAndConditions.put("headOnly", headOnly);
        argumentAndConditions.put("delete", delete);
        argumentAndConditions.put("range", range);

        for (Map.Entry<String,Boolean> entry : argumentAndConditions.entrySet()) {
            String parameter = entry.getKey();
            Boolean condition = entry.getValue();
            if (condition) {
                arguments.add(parameter);
            }
        }
        return arguments;
    }

    public String parseParameters() {
        String parameterString = "";
        if (parameters() != null) {
            ParameterParser parameters = new ParameterParser(parameters());
            parameterString = parameters.convertToBodyText();
        }
        return parameterString;
    }

    public byte[] getResponse() {
        List<String> arguments = getResponseBodyArguments();
        Map<String, String> headerFields = parseHeaders();
        String parameters = parseParameters();

        BodyContent bodyContent = new BodyContent(method, route);
        bodyContent.sendRequestBody(body);
        bodyContent.sendArguments(arguments);
        bodyContent.sendHeaderFields(headerFields);
        bodyContent.sendUrlParameters(parameters);

        ResponseHeaders headers = new ResponseHeaders(method, route);
        headers.sendArguments(arguments);

        byte[] header = headers.getHeader();
        byte[] body = bodyContent.getBody();
        return SharedUtilities.addByteArrays(header, body);
    }

    public byte[] processRequest() {
        return getResponse();
    }
}
