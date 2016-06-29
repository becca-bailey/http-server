package com.rnelson.server;

import com.rnelson.server.FileHandler;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void editPageContent() {
        List<String> updateMethods = Arrays.asList("POST", "PUT");
        if (method.equals("DELETE")) {
            deletePageContent();
        }
        if (updateMethods.contains(method)) {
            updatePageContent();
        }
        if (method.equals("PATCH")) {
            updateFileContent();
        }
    }

    private void updatePageContent() {
        pageContent.put(route, requestBody.getBytes());
    }

    private void updateFileContent() {
        File file = new File("public" + route);
        FileHandler handler = new FileHandler(file);
        handler.updateFileContent(requestBody);
        pageContent.put(route, requestBody.getBytes());
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
        editPageContent();
        byte[] content = emptyContent;
        if (pageHasContent()) {
            content = getContentForConditions();
            return applyRange(content);
        } else {
            return content;
        }
    }

    private byte[] getContentForConditions() {
        Map<byte[], Boolean> contentWithConditions = getContentWithConditions();
        for (Map.Entry<byte[], Boolean> entry : contentWithConditions.entrySet()) {
            byte[] pageContent = entry.getKey();
            Boolean condition = entry.getValue();
            if (condition) {
                return pageContent;
            }
        }
        return emptyContent;
    }

    private Map<byte[], Boolean> getContentWithConditions() {
        Map<byte[], Boolean> contentWithConditions = new HashMap<>();
        contentWithConditions.put(pageContent.get(route), existingPageContent());
        contentWithConditions.put(requestBody.getBytes(), responseEchoesRequest());
        contentWithConditions.put(parameters.getBytes(), contentIncludesParameters());
        return contentWithConditions;
    }

    private Boolean contentIncludesParameters() {
        return route.equals("/parameters");
    }

    private Boolean isPostEcho() {
        return method.equals("POST") && route.equals("/echo");
    }

    private Boolean pageHasContent() {
        if (isPostEcho()) {
            return true;
        }
        return !sendHeaderOnly();
    }

    private byte[] applyRange(byte[] content) {
        byte[] contentInRange = content;
        if (contentRange()) {
            int minRange = getMinRange(content.length);
            int maxRange = getMaxRange(content.length);
            byte[] partialContent = new byte[(maxRange - minRange) + 1];
            System.arraycopy(content, minRange, partialContent, 0, partialContent.length);
            contentInRange = partialContent;
        }
        return contentInRange;
    }

    private Boolean existingPageContent() {
        return pageContent.get(route) != null;
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
        int highestIndex = contentLength - 1;
        if (finalBytes(range)) {
            return finalMinMax(range, highestIndex);
        } else if (bytesToEnd(range)) {
            return minMaxToEnd(range, highestIndex);
        } else {
            return givenMinAndMax(range);
        }
    }

    private Integer[] finalMinMax(String range, int highestIndex) {
        int difference = Integer.parseInt(SharedUtilities.findMatch("\\d+", range, 0)) - 1;
        int min = highestIndex - difference;
        int max = highestIndex;
        return toIntArray(min, max);
    }

    private Integer[] minMaxToEnd(String range, int highestIndex) {
        int min = Integer.parseInt(SharedUtilities.findMatch("\\d*", range, 0));
        int max = highestIndex;
        return toIntArray(min, max);
    }

    private Integer[] givenMinAndMax(String range) {
        String[] splitRange = range.split("-");
        int min = Integer.parseInt(splitRange[0]);
        int max = Integer.parseInt(splitRange[1]);
        return toIntArray(min, max);
    }

    private Integer[] toIntArray(int min, int max) {
        return new Integer[]{min,max};
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
