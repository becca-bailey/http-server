package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.Response;

import java.util.Set;

public class FormController implements Controller {

    @Override
    public byte[] get() {
        return ("This is a form").getBytes();
    }

    @Override
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();

    }

    @Override
    public byte[] post() {
        return Response.twoHundred.getBytes();
    }

    @Override
    public byte[] put() {
        return Response.twoHundred.getBytes();
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
    public void sendRequestBody(String body) {

    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {

    }
}
