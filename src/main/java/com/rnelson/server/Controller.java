package com.rnelson.server;

public interface Controller {

    byte[] get();

    byte[] head();

    byte[] post();

    byte[] put();

    byte[] patch();

    byte[] options();

    void sendRequestBody(String body);
}
