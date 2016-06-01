package com.rnelson.server;

import java.io.*;
import java.net.*;

public class ServerRunner implements Runnable {
    int serverPort;

    public ServerRunner(int port) {
        this.serverPort = port;
    }

    private void echo (OutputStreamWriter out, BufferedReader in) throws IOException {
        String request = in.readLine();
        if (request.equals("GET /echo HTTP/1.1")) {
            out.write("HTTP/1.1 200 OK\r\n\r\n" + request + "\r\n");
        }
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
