package com.rnelson.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner implements Runnable {
    int serverPort;

    public ServerRunner(int port) {
        this.serverPort = port;
    }

    public String echoResponse(String request) {
        return ("Echo: " + request);
    }

    private void echo (OutputStreamWriter out, BufferedReader in) throws IOException {
        String request;
//        request = in.readLine();
        request = "GET /echo";
        out.write(echoResponse(request));
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
