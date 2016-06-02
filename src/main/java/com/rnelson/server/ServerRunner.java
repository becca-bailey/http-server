package com.rnelson.server;

import java.io.*;
import java.net.*;

public class ServerRunner implements Runnable {
    int serverPort;

    public ServerRunner(int port) {
        this.serverPort = port;
    }

    public String getResponse(String request) {
        String response = null;
        String okayResponse = "HTTP/1.1 200 OK\r\n\r\n";
        if (request.equals("GET /echo HTTP/1.1")) {
            response = okayResponse + request;
        }
        if (request.equals("HEAD / HTTP/1.1")) {
            response = okayResponse;
        }
        if (request.equals("GET / HTTP/1.1")) {
            response = okayResponse;
        }
        return response;
    }

    private void echo (OutputStreamWriter out, BufferedReader in) throws IOException {
        String request = in.readLine();
        String response = getResponse(request);
        out.write(response);
        out.close();
    }

    @Override
    public void run() {
        System.out.println("Listening on port " + serverPort + "...");
        while (true) {
            try (
                    ServerSocket serverSocket = new ServerSocket(serverPort);
                    Socket clientSocket = serverSocket.accept();

                    OutputStreamWriter out =
                            new OutputStreamWriter(clientSocket.getOutputStream());
                    BufferedReader in =
                            new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                this.echo(out, in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
