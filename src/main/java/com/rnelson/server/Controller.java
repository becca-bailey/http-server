package com.rnelson.server;

import java.io.File;
import java.util.Set;

public interface Controller {

    String body = null;
    Set<String> methodOptions = null;
    Boolean isAuthorized = null;

    byte[] get();

    byte[] head();

    byte[] post();

    byte[] put();

    byte[] patch();

    byte[] options();

    byte[] delete();

    byte[] redirect();

    void sendRequestBody(String body);

    void sendMethodOptions(Set<String> methodOptions);

    void sendFile(File file);

    void isAuthorized(Boolean isAuthorized);
}
