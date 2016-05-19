package com.rnelson.server;

import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        String parameter = args[0];
        int portNumber = Integer.parseInt(args[1]);
        System.out.println("Echo: hello");
    }
}
