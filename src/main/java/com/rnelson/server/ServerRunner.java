package com.rnelson.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner implements Runnable {
    int serverPort;

    public ServerRunner(int port) {
        this.serverPort = port;
    }

    private void echo (PrintWriter out, BufferedReader in) throws IOException {
        String request;
        while((request = in.readLine()) != null) {
            out.println("Echo: " + request);
        }
    }

    @Override
    public void run() {
        try (
                ServerSocket serverSocket = new ServerSocket(serverPort);
                Socket clientSocket = serverSocket.accept();

                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            this.echo(out, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
