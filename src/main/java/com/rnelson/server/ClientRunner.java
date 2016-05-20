package com.rnelson.server;

import java.io.*;
import java.net.Socket;

public class ClientRunner implements Runnable {
    String hostName;
    Integer port;

    public ClientRunner(String hostName, int port) throws IOException {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void run() {
        try (
                Socket clientSocket = new Socket(hostName, port);
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String request;
            while ((request = stdIn.readLine()) != null) {
                out.println(request);
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
