package com.rnelson.server;

import com.rnelson.server.utilities.http.HttpMethods;

public class Controller {

    public byte[] getResponse(String method) {
        byte[] response = new byte[0];
        if (method.equals(HttpMethods.get)) {
            return this.get();
        }
        if (method.equals(HttpMethods.head)) {
            return this.head();
        }
        return response;
    }

    public byte[] get() {
        return new byte[0];
    }
    public byte[] head() { return new byte[0]; }
}
