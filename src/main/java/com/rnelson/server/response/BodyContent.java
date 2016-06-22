package com.rnelson.server.response;

import com.rnelson.server.file.DirectoryHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodyContent {
    private String method;
    private String route;
    private List<String> arguments;
    private String requestBody;
    private String parameters;
    private Map<String, String> headerFields;
    private String publicDirectory = "public";
    private Integer minRange;
    private Integer maxRange;
    private byte[] emptyContent = new byte[0];

    public static Map<String, byte[]> pageContent = new HashMap<String, byte[]>();



    public BodyContent(String method, String route) {
        this.method = method;
        this.route = route;
        // put this somewhere else
    }

    private void populatePageContent() {
        // refactor this
        if (method.equals("DELETE")) {
            deletePageContent();
        }
        if (method.equals("POST") || method.equals("PUT")) {
            pageContent.put(route, requestBody.getBytes());
        }
        pageContent.put("/coffee", ("I'm a teapot").getBytes());
        DirectoryHandler directoryHandler = new DirectoryHandler(publicDirectory);
        pageContent.put("/", (directoryHandler.getDirectoryLinks()).getBytes());
        directoryHandler.handleAllFiles();
        pageContent.put("/parameters", parameters.getBytes());

    }

    private Boolean responseEchoesRequest() {
        return arguments.contains("echoResponseBody");
    }

    private Boolean sendHeaderOnly() {
        return arguments.contains("headOnly");
    }

    private Boolean contentRange() {
        return arguments.contains("range");
    }

    public byte[] getBody() {
        // refactor this
        populatePageContent();
        byte[] content = emptyContent;
        if (pageContent.get(route) != null) {
            content = pageContent.get(route);
        }
        if (sendHeaderOnly()) {
            content = emptyContent;
        }
        if (responseEchoesRequest()) {
            content = requestBody.getBytes();
        }
        if (contentRange()) {
            byte[] partialContent = new byte[maxRange - minRange];
            System.arraycopy(content, minRange, partialContent, 0, partialContent.length);
            content = partialContent;
        }
        return content;
    }

    public void sendArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void sendHeaderFields(Map<String, String> headerFields) {
        this.headerFields = headerFields;
    }

    public void sendRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    private void deletePageContent() {
        pageContent.put(route, emptyContent);
    }

    public void sendUrlParameters(String parametersAsString) {
        this.parameters = parametersAsString;
    }

    public void contentRange(int min, int max) {
        this.minRange = min;
        this.maxRange = max;
    }
}
