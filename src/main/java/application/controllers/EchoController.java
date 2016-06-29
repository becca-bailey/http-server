package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.utilities.Response;

import java.io.File;
import java.util.Set;

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
    public byte[] redirect() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public void sendRequestBody(String body) {
        this.requestBody = body;
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
