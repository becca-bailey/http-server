package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import application.Header;

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
    public void sendResponseData(ResponseData responseData) {

    }
}
