package com.rnelson.server;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void connect(String hostName, int portNumber) throws IOException {
        Socket clientSocket = new Socket(hostName, portNumber);
    }

    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 5000;
        connect(hostName, portNumber);
    }
}
