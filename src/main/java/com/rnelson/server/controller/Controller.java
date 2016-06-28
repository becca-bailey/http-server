package com.rnelson.server.controller;

import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.http.HttpMethods;

public class Controller {

    private byte[] twoHundred = (Response.status(200) + "\r\n\r\n").getBytes();

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

    //declare methods

    public byte[] get() {
        return new byte[0];
    }
    public byte[] head() { return new byte[0]; }
}
