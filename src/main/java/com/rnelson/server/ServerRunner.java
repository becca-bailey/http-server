package com.rnelson.server;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.net.*;

public class ServerRunner implements Runnable {
    int serverPort;
    Boolean running = true;

    public ServerRunner(int port) {
        this.serverPort = port;
    }

    private void respondToRequest (OutputStreamWriter out, BufferedReader in) throws IOException {
        String request = in.readLine();
        RequestHandler handler = new RequestHandler(request);
        out.write(handler.getResponse());
        out.close();
    }

    @Override
    public void run() {
        while (running) {
            try (
                    ServerSocket serverSocket = new ServerSocket(serverPort);
                    Socket clientSocket = serverSocket.accept();

                    OutputStreamWriter out =
                            new OutputStreamWriter(clientSocket.getOutputStream());
                    BufferedReader in =
                            new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                respondToRequest(out, in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
