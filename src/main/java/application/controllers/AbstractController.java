package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import com.rnelson.server.utilities.Response;

public abstract class AbstractController implements Controller {

    public byte[] get() {
        return Response.methodNotAllowed.getBytes();
    }

    public byte[] head() {
        return Response.methodNotAllowed.getBytes();
    }

    public byte[] post() {
        return Response.methodNotAllowed.getBytes();
    }

    public byte[] put() {
        return Response.methodNotAllowed.getBytes();
    }

    public byte[] patch() {
        return Response.methodNotAllowed.getBytes();
    }

    public byte[] options() {
        return Response.methodNotAllowed.getBytes();
    }

    public byte[] delete() {
        return Response.methodNotAllowed.getBytes();
    }

    public void sendResponseData(ResponseData responseData) {

    }
}
