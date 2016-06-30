package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import application.Header;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

public class CoffeeController implements Controller {

    @Override
    public byte[] get() {
        byte[] body = ("I'm a teapot").getBytes();
        Header header = new Header(418);
        byte[] responseHeader = header.getResponseHeader();
        return SharedUtilities.addByteArrays(responseHeader, body);
    }

    @Override
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] post() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] put() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] patch() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] options() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] delete() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public void sendResponseData(ResponseData responseData) {

    }
}

