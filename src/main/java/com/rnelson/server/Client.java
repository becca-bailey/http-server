package com.rnelson.server;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 5000;
        Socket clientSocket = new Socket(hostName, portNumber);
    }
}
