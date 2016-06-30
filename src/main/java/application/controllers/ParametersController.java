package application.controllers;

import com.rnelson.server.Controller;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class ParametersController implements Controller {
    private Map<String,String> parameters;

    @Override
    public byte[] get() {
        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            text.append(entry.getKey());
            text.append(" = ");
            text.append(entry.getValue());
            text.append("\n");
        }
        return text.toString().getBytes();
    }

    @Override
    public byte[] head() {
        return new byte[0];
    }

    @Override
    public byte[] post() {
        return new byte[0];
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
    public byte[] delete() {
        return new byte[0];
    }

    @Override
    public byte[] redirect() {
        return new byte[0];
    }

    @Override
    public void sendRequestData(Map<String, String> data) {
        this.parameters = data;
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
