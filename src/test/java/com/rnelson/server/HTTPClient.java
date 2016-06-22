package com.rnelson.server;

import com.rnelson.server.utilities.SharedUtilities;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.params.HttpParams;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HTTPClient implements Runnable {
    public String hostName;
    public Integer portNumber;
    private Socket clientSocket;
    private OutputStreamWriter out;

    public String requestLine = "";
    public String body = "";
    private HttpResponse httpResponse;
    private String response;
    private byte[] responseBytes;
    private String statusLine;
    private String[] headerLines;

    public HTTPClient(String hostName, Integer portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public String fullRequest() {
        List<String> requestLines = Arrays.asList(requestLine);
        return String.join("\r\n", requestLines) + "\r\n\r\n" + body;
    }

    private void sendRequestToServer() throws IOException {
        clientSocket = new Socket(hostName, portNumber);
        out = new OutputStreamWriter(clientSocket.getOutputStream());
        out.write(fullRequest());
    }

    private String readServerResponse(InputStream in) throws IOException {
        StringBuilder responseFromServer = new StringBuilder();
        while (in.read() != -1) {
            responseFromServer.append(in.read());
        }
        return responseFromServer.toString();
    }

    public void setResponseVariables(String response) {
        this.response = response;
        String[] responseLines = response.split("\r\n");
        this.statusLine = responseLines[0];
    }

    private void getResponseFromServer(HttpResponse response) throws IOException {
        // I still need to get HttpResponse
        this.httpResponse = response;
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            responseBytes = IOUtils.toByteArray(entity.getContent());
        } else {
            responseBytes = new byte[0];
        }
    }

    public void connect() {
        try {
            sendRequestToServer();
            getResponseFromServer();
            //http response goes here
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequestHeader(String method, String route) {
        this.requestLine = method.toUpperCase() + " " + route + " HTTP/1.1";
    }

    public void sendRequestBody(String body) {
        this.body = body;
    }

    private void closeOutputStream() throws IOException {
        out.flush();
        out.close();
    }

    public Integer getResponseCode() {
        return Integer.parseInt(SharedUtilities.findMatch("\\d{3}", statusLine, 0));
    }

    public String getResponseBody() {
        return SharedUtilities.findMatch("(\\r\\n\\r\\n)(.*)\\z", response, 2);
    }

    public String getResponseHeader() {
        return SharedUtilities.findMatch("(\\A.*)(\\r\\n\\r\\n)", response, 0);
    }

    public byte[] getResponseBytes() {
        return response.getBytes();
    }

    public String[] splitHeader() {
        String header = getResponseHeader();
        return header.split("\r\n");
    }

    public String getHeaderField(String fieldName) throws NoSuchFieldException {
        for (String headerLine : splitHeader()) {
            if (headerLine.contains(fieldName)) {
                return SharedUtilities.findMatch("(:\\s*)(\\S)", headerLine, 2);
            }
        }
        throw new NoSuchFieldException("Field doesn't exist");
    }

    public void disconnect() throws IOException {
        clientSocket.close();
        closeOutputStream();
    }

    @Override
    public void run() {
        connect();
    }
}

