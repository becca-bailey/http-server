package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import application.Header;
import com.rnelson.server.utilities.Response;

public class RedirectController implements Controller {
    @Override
    public byte[] get() {
        Config.redirect = true;
        Header header = new Header(302);
        header.includeLocation("http://localhost:5000/");
        return header.getResponseHeader();
    }

    @Override
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();   }

    @Override
    public byte[] post() {
        return Response.methodNotAllowed.getBytes();    }

    @Override
    public byte[] put() {
        return Response.methodNotAllowed.getBytes();    }

    @Override
    public byte[] patch() {
        return Response.methodNotAllowed.getBytes();    }

    @Override
    public byte[] options() {
        return Response.methodNotAllowed.getBytes();    }

    @Override
    public byte[] delete() {
        return Response.methodNotAllowed.getBytes();    }

    @Override
    public void sendResponseData(ResponseData responseData) {

    }
}
