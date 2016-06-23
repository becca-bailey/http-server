package com.rnelson.server;

import com.rnelson.server.utilities.SharedUtilities;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.*;
import org.apache.commons.codec.binary.Base64;


import java.io.IOException;
import java.util.*;

public class HTTPClient {
    public String hostName;
    public Integer portNumber;
    private String url;
    private CloseableHttpClient httpclient;
    private CloseableHttpResponse response;

    public String requestLine = "";
    public String body = "";

    public String username;
    public String password;

    private String responseAsString;
    private String statusLine;
    private String[] headerLines;
    private byte[] responseBody = new byte[0];
    private String method;
    private String requestUrl;
    private HashMap<String, CloseableHttpResponse> methods;
    private Boolean isRange = false;
    private Boolean hasCredentials = false;
    private String requestedRange;

    public HTTPClient(String hostName, Integer portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.url = "http://" + hostName + ":" + portNumber;
        httpclient = HttpClients.createDefault();
    }

    public void sendRequestHeader(String method, String route) {
        this.method = method;
        this.requestUrl = url + route;
        this.requestLine = method.toUpperCase() + " " + route + " HTTP/1.1";
    }

    public void sendRequestBody(String body) {
        this.body = body;
    }

    public void setRange(String range) throws IOException {
        isRange = true;
        requestedRange = "bytes=" + range;
        httpclient = HttpClients.custom().build();
    }

    public void sendCredentials(String username, String password) {
        hasCredentials = true;
        this.username = username;
        this.password = password;
        httpclient = HttpClients.custom().build();
    }

    public void connect() {
        try {
            sendRequestToServer();
            getResponseFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestToServer() throws IOException {
        response = getResponseForMethod();
    }

    public String fullRequest() {
        List<String> requestLines = Arrays.asList(requestLine);
        return String.join("\r\n", requestLines) + "\r\n\r\n" + body;
    }

    private CloseableHttpResponse getResponseForMethod() {
        // refactor this!!
        CloseableHttpResponse response = null;
        try {
            if (method.equals("POST")) {
                response = post();
            } else if (method.equals("OPTIONS")) {
                response = options();
            } else if (method.equals("HEAD")) {
                response = head();
            } else if (method.equals("PUT")) {
                response = put();
            } else if (method.equals("DELETE")) {
                response = delete();
            } else {
                response = get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private CloseableHttpResponse get() throws IOException {
        if (hasCredentials) {
            String credentials = username + ":" + password;
            String encodedCredentials = "Basic " + base64Encode(credentials);
            HttpUriRequest request = RequestBuilder.get().setUri(requestUrl).setHeader(HttpHeaders.AUTHORIZATION, encodedCredentials).build();
            return httpclient.execute(request);
        }
        if (isRange) {
            HttpUriRequest request = RequestBuilder.get().setUri(requestUrl).setHeader(HttpHeaders.RANGE, requestedRange).build();
            return httpclient.execute(request);
        } else {
            HttpGet httpget = new HttpGet(requestUrl);
            return httpclient.execute(httpget);
        }
    }

    private CloseableHttpResponse post() throws IOException {
        HttpPost httppost = new HttpPost(requestUrl);
        httppost.setEntity(new ByteArrayEntity(body.getBytes()));
        return httpclient.execute(httppost);
    }

    private CloseableHttpResponse options() throws IOException {
        HttpOptions httpoptions = new HttpOptions(requestUrl);
        return httpclient.execute(httpoptions);
    }

    private CloseableHttpResponse head() throws IOException {
        HttpHead httphead = new HttpHead(requestUrl);
        return httpclient.execute(httphead);
    }

    private CloseableHttpResponse put() throws IOException {
        HttpPut httpput = new HttpPut(requestUrl);
        httpput.setEntity(new ByteArrayEntity(body.getBytes()));
        return httpclient.execute(httpput);
    }

    private CloseableHttpResponse delete() throws IOException {
        HttpDelete httpdelete = new HttpDelete(requestUrl);
        return httpclient.execute(httpdelete);
    }

    public void setResponseVariables(String response) {
        this.responseAsString = response;
        String[] responseLines = response.split("\r\n");
        this.statusLine = responseLines[0];
    }

    private void getResponseFromServer() throws IOException {
        this.statusLine = response.getStatusLine().toString();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            responseBody = IOUtils.toByteArray(entity.getContent());
        }
    }

    public String getResponseBody() throws IOException {
        String response = new String(responseBody);
        return new String(responseBody);
    }

    public Integer getResponseCode() {
        return Integer.parseInt(SharedUtilities.findMatch("\\d{3}", statusLine, 0));
    }

    public String getResponseHeader() {
        StringBuilder responseLines = new StringBuilder();
        responseLines.append(response.getStatusLine());
        for (Header header : response.getAllHeaders()) {
            responseLines.append("\r\n");
            responseLines.append(header);
        }
        responseLines.append("\r\n\r\n");
        return responseLines.toString();
    }

    public String getStatusLine() {
        return response.getStatusLine().toString();
    }

    public byte[] getResponseBytes() {
        return responseBody;
    }

    public String[] splitHeader() {
        String header = getResponseHeader();
        return header.split("\r\n");
    }

    public String getHeaderField(String fieldName) throws NoSuchFieldException {
        for (String headerLine : splitHeader()) {
            if (headerLine.contains(fieldName)) {
                return SharedUtilities.findMatch("(:\\s*)(\\S*)", headerLine, 2);
            }
        }
        throw new NoSuchFieldException("Field doesn't exist");
    }

    private String base64Encode(String s) {
        byte[] encodedBytes = Base64.encodeBase64(s.getBytes());
        return new String(encodedBytes);
    }

    public void disconnect() throws IOException {
        httpclient.close();
        response.close();
    }
}

