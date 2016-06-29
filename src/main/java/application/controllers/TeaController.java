package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.Header;
import com.rnelson.server.Response;

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
    public void sendRequestBody(String body) {

    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {

    }
}
