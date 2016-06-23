package com.rnelson.server.response;

import com.rnelson.server.utilities.SharedUtilities;

import java.util.*;

public class BodyContent {
    private String method;
    private String route;
    private String requestBody;
    private String parameters;
    private String publicDirectory = "public";


    private List<String> arguments;
    private Map<String, String> headerFields;
    private byte[] emptyContent = new byte[0];
    public static Map<String, byte[]> pageContent = new HashMap<String, byte[]>();


    public BodyContent(String method, String route) {
        this.method = method;
        this.route = route;
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

    private void changePageContent() {
        if (method.equals("DELETE")) {
            deletePageContent();
        }
        if (method.equals("POST") || method.equals("PUT")) {
            pageContent.put(route, requestBody.getBytes());
        }
        BodyContent.pageContent.put("/parameters", parameters.getBytes());

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
        changePageContent();
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
            Integer minRange = getMinRange(content.length);
            Integer maxRange = getMaxRange(content.length);
            byte[] partialContent = new byte[(maxRange - minRange) + 1];
            System.arraycopy(content, minRange, partialContent, 0, partialContent.length);
            content = partialContent;
        }
        return content;
    }

    public Boolean finalBytes(String byteRange) {
        String match = SharedUtilities.findMatch("^[-]\\d*", byteRange, 0);
        return match != null;
    }

    public Boolean bytesToEnd(String byteRange) {
        String match = SharedUtilities.findMatch("\\d*-$", byteRange, 0);
        return match != null;
    }

    public Integer[] minAndMaxInRange(String range, Integer contentLength) {
        Integer min;
        Integer max;
        if (finalBytes(range)) {
            int difference = Integer.parseInt(SharedUtilities.findMatch("\\d+", range, 0)) - 1;
            min = (contentLength - 1) - difference;
            max = (contentLength - 1);
        } else if (bytesToEnd(range)) {
            min = Integer.parseInt(SharedUtilities.findMatch("\\d*", range, 0));
            max = (contentLength - 1);
        } else {
            String[] splitRange = range.split("-");
            min = Integer.parseInt(splitRange[0]);
            max = Integer.parseInt(splitRange[1]);
        }
        return new Integer[]{min, max};
    }

    private Integer getMinRange(Integer contentLength) {
        String rangeBytes = headerFields.get("Range");
        String range = SharedUtilities.findMatch("(bytes=)(.*)", rangeBytes, 2);
        Integer[] minAndMax = minAndMaxInRange(range, contentLength);
        return minAndMax[0];
    }

    private Integer getMaxRange(Integer contentLength) {
        String rangeBytes = headerFields.get("Range");
        String range = SharedUtilities.findMatch("(bytes=)(.*)", rangeBytes, 2);
        Integer[] minAndMax = minAndMaxInRange(range, contentLength);
        return minAndMax[1];
    }

}
