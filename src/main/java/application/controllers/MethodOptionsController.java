package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import application.Header;
import com.rnelson.server.utilities.Response;

import java.util.Set;

public class MethodOptionsController implements Controller {
    private String body;
    private Set<String> methodOptions;

    @Override
    public byte[] get() {
        return Response.methodNotAllowed.getBytes();
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
        Header header = new Header(200);
        header.includeOptions(methodOptions);
        return header.getResponseHeader();
    }

    @Override
    public byte[] delete() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.body = responseData.requestBody;
        this.methodOptions = responseData.methodOptions;
    }
}
