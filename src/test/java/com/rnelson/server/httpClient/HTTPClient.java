package com.rnelson.server.httpClient;

import com.rnelson.server.request.Request;
import com.rnelson.server.utilities.SharedUtilities;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;

public class HTTPClient {
    final String hostName;
    final int portNumber;
    private final String url;
    private CloseableHttpClient httpclient;
    private CloseableHttpResponse response;

    String requestLine = "";
    public String body = "";

    private String username;
    private String password;

    private String statusLine;
    private int statusCode;
    private byte[] responseBody = new byte[0];
    private String method;
    private String requestUrl;
    private Boolean isRange = false;
    private Boolean hasCredentials = false;
    private String requestedRange;
    private String etag;

    public HTTPClient(String hostName, Integer portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.url = "http://" + hostName + ":" + portNumber;
        httpclient = HttpClientBuilder.create().disableRedirectHandling().build();
    }

    public void sendRequestHeader(String method, String route) {
        this.method = method;
        this.requestUrl = url + route;
        this.requestLine = fullRequestLine(method, route);
    }

    private String fullRequestLine(String method, String route) {
        return method.toUpperCase() + " " + route + " HTTP/1.1";
    }

    public void sendRequestBody(String body) {
        this.body = body;
    }

    public void setRange(String range) {
        isRange = true;
        requestedRange = "bytes=" + range;
        httpclient = HttpClients.custom().build();
    }

    public void setEtag(String etag) {
        this.etag = etag;
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

    private void sendRequestToServer() {
        response = getResponseForMethod();
    }

    private CloseableHttpResponse getResponseForMethod() {
        CloseableHttpResponse response = null;
        try {
            switch (method) {
                case "POST":
                    response = post();
                    break;
                case "OPTIONS":
                    response = options();
                    break;
                case "HEAD":
                    response = head();
                    break;
                case "PUT":
                    response = put();
                    break;
                case "DELETE":
                    response = delete();
                    break;
                case "PATCH":
                    response = patch();
                    break;
                case "GET":
                    response = get();
                    break;
                default:
                    response = makeInvalidRequest();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException ignored) {
        }
        return response;
    }

    private CloseableHttpResponse get() throws IOException {
        if (hasCredentials) {
            return getWithCredentials();
        }
        if (isRange) {
            return getRange();
        }
        else {
            HttpGet httpget = new HttpGet(requestUrl);
            return httpclient.execute(httpget);
        }
    }

    private CloseableHttpResponse getWithCredentials() throws IOException {
        String credentials = username + ":" + password;
        String encodedCredentials = "Basic " + base64Encode(credentials);
        HttpUriRequest request = RequestBuilder.get().setUri(requestUrl).setHeader(HttpHeaders.AUTHORIZATION, encodedCredentials).build();
        return httpclient.execute(request);
    }

    private CloseableHttpResponse getRange() throws IOException {
        HttpUriRequest request = RequestBuilder.get().setUri(requestUrl).setHeader(HttpHeaders.RANGE, requestedRange).build();
        return httpclient.execute(request);
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

    private CloseableHttpResponse patch() throws IOException {
        HttpPatch httppatch = new HttpPatch(requestUrl);
        httppatch.setHeader(HttpHeaders.ETAG, etag);
        httppatch.setEntity(new ByteArrayEntity(body.getBytes()));
        return httpclient.execute(httppatch);
    }

    private CloseableHttpResponse makeInvalidRequest() throws URISyntaxException, IOException {
        InvalidRequest invalid = new InvalidRequest(method, requestUrl);
        return httpclient.execute(invalid);
    }

    void setResponseVariables(String response) {
        String[] responseLines = response.split("\r\n");
        this.statusLine = responseLines[0];
    }

    private void getResponseFromServer() throws IOException {
        this.statusLine = response.getStatusLine().toString();
        this.statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            responseBody = IOUtils.toByteArray(entity.getContent());
        }
    }

    public String getResponseBody() {
        return new String(responseBody);
    }

    public int getResponseCode() {
        return statusCode;
    }

    private String getResponseHeader() {
        StringBuilder responseLines = new StringBuilder();
        responseLines.append(response.getStatusLine());
        for (Header header : response.getAllHeaders()) {
            responseLines.append("\r\n");
            responseLines.append(header);
        }
        responseLines.append("\r\n\r\n");
        return responseLines.toString();
    }

    public byte[] getResponseBytes() {
        return responseBody;
    }

    private String[] splitHeader() {
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

    public void mockRequest(String method, String route) {
        String requestLine = fullRequestLine(method, route);
        Request request = new Request(requestLine + "\r\n\r\n");
        request.logRequest();
    }
}

