package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.Header;
import com.rnelson.server.Response;

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
    public void sendRequestBody(String body) {
        this.body = body;

    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {
        this.methodOptions = methodOptions;
    }
}
