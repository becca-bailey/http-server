package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.Response;

/**
 * Created by Becca on 6/28/16.
 */
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
        this.requestBody = body;
    }
}
