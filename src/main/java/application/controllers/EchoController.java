package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import com.rnelson.server.utilities.Response;

public class EchoController implements Controller {
    String requestBody;

    @Override
    public byte[] get() {
        return Response.twoHundred.getBytes();
    }

    @Override
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] post() {
        return (Response.twoHundred + requestBody).getBytes();
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
        this.requestBody = responseData.requestBody;
    }
}
