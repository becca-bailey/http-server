package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.header.Header;

import java.io.File;
import java.util.Set;

public class RedirectController implements Controller {
    @Override
    public byte[] get() {
        Config.redirect = true;
        Header header = new Header(302);
        header.includeLocation("http://localhost:5000");
        return header.getResponseHeader();
    }

    @Override
    public byte[] head() {
        return new byte[0];
    }

    @Override
    public byte[] post() {
        return new byte[0];
    }

    @Override
    public byte[] put() {
        return new byte[0];
    }

    @Override
    public byte[] patch() {
        return new byte[0];
    }

    @Override
    public byte[] options() {
        return new byte[0];
    }

    @Override
    public byte[] delete() {
        return new byte[0];
    }

    @Override
    public byte[] redirect() {
        return new byte[0];
    }

    @Override
    public void sendRequestBody(String body) {

    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {

    }

    @Override
    public void sendFile(File file) {

    }

    @Override
    public void isAuthorized(Boolean isAuthorized) {

    }
}
