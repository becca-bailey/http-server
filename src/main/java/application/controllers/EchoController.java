package application.controllers;

import com.rnelson.server.ResponseData;
import com.rnelson.server.utilities.Response;

public class EchoController extends AbstractController {
    private String requestBody;

    @Override
    public byte[] get() {
        return Response.twoHundred.getBytes();
    }

    @Override
    public byte[] post() {
        return (Response.twoHundred + requestBody).getBytes();
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.requestBody = responseData.requestBody;
    }
}
