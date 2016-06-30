package com.rnelson.server;

public interface Controller {

    byte[] get();

    byte[] head();

    byte[] post();

    byte[] put();

    byte[] patch();

    byte[] options();

    byte[] delete();

    void sendResponseData(ResponseData responseData);
}
