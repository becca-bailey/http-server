package com.rnelson.server;

public class Controller {
    public byte[] getResponse(String method) {
        byte[] response = new byte[0];
        if (method.equals("GET")) {
            return this.get();
        }
        return response;
    }

    public byte[] get() {
        return new byte[0];
    }
}
