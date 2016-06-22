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
        return SharedUtilities.findMatch("([?])(.*)", requestLines[0], 2);
    }

    public byte[] getResponse() {
        // refactor this
        Response response = new Response(method, route);
        if (response.echoesBody()) {
            response.sendRequestBody(body);
        }
        if (parameters() != null) {
            ParameterParser parameters = new ParameterParser(parameters());
            body = parameters.convertToBodyText();
            response.sendRequestBody(body);
        }
        byte[] header = response.getHeader();
        byte[] body = response.getBody();
        return SharedUtilities.addByteArrays(header, body);
    }

    public byte[] processRequest() {
        return getResponse();
    }
}
