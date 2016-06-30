package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.header.Header;
import com.rnelson.server.utilities.Response;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class TeaController implements Controller {

    @Override
    public byte[] get() {
        Header header = new Header(200);
        return header.getResponseHeader();
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
    public byte[] redirect() {
        return new byte[0];
    }

    @Override
    public void sendRequestData(Map<String, String> data) {

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
