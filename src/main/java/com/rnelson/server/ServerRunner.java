package com.rnelson.server;

import java.io.*;
import java.net.*;

class ServerRunner implements Runnable {
    private final int serverPort;
    private Boolean running = true;

    ServerRunner(int port) {
        this.serverPort = port;
    }

    private String getFullRequest(BufferedReader in) throws IOException {
        StringBuilder request = new StringBuilder();
        request.append(in.readLine());
        request.append("\n");
        while(in.ready()) {
            request.append((char) in.read());
        }
        return request.toString();
    }

    private void respondToRequest (OutputStreamWriter out, BufferedReader in) throws IOException {
        String request = getFullRequest(in);
        RequestHandler handler = new RequestHandler(request);
        String response = handler.processRequest();
        out.write(response);
        out.close();
    }

    public void stop() {
        this.running = false;
    }

    public Boolean isRunning() {
        return running;
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
                            new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                respondToRequest(out, in);
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
