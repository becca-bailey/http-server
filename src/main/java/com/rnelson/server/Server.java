package com.rnelson.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket clientSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}