package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import application.Header;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.Map;

public class ParametersController implements Controller {
    private Map<String,String> parameters;

    @Override
    public byte[] get() {
        Header header = new Header(200);
        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            text.append(entry.getKey());
            text.append(" = ");
            text.append(entry.getValue());
            text.append("\n");
        }
        byte[] parametersInBody = text.toString().getBytes();
        return SharedUtilities.addByteArrays(header.getResponseHeader(), parametersInBody);
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
    public void sendResponseData(ResponseData responseData) {
        this.parameters = responseData.parameters;
    }
}
