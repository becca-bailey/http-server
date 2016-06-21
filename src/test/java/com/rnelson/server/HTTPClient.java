package com.rnelson.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class HTTPClient {
    private String hostName;
    private Integer portNumber;
    private Socket clientSocket;

    private String requestLine = "";
    private String body = "";
    private String[] responseLines;

    public HTTPClient(String hostName, Integer portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    private String fullRequest() {
        List<String> requestLines = Arrays.asList(requestLine, body);
        return String.join("\r\n", requestLines) + "\r\n\r\n";
    }

    private void sendRequestToServer() throws IOException {
        clientSocket = new Socket(hostName, portNumber);
        OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
        out.write(fullRequest());
        closeOutputStream(out);
    }

    private String readServerResponse(BufferedReader in) throws IOException {
        StringBuilder responseFromServer = new StringBuilder();
        responseFromServer.append(in.readLine());
        while (in.ready()) {
            responseFromServer.append((char) in.read());
        }
        return responseFromServer.toString();
    }

    private void getResponseFromServer() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String response = readServerResponse(in);
        this.responseLines = response.split("\r\n");
    }

    public void connect() {
        try {
            sendRequestToServer();
            getResponseFromServer();
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

    private void closeOutputStream(OutputStreamWriter out) throws IOException {
        out.flush();
        out.close();
    }

    public Integer getResponseCode() {

    }

    public String getResponseBody() {

    }

    public byte[] getResponseBytes() {

    }

    public String getHeaderField(String fieldName) {

    }

    public void disconnect() {

    }
}

