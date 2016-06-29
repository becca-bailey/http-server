package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.Directory;
import com.rnelson.server.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.Set;

public class RootController implements Controller {

    @Override
    public byte[] get() {
        Directory directory = new Directory("public/");
        byte[] body =  directory.getDirectoryLinks().getBytes();
        return SharedUtilities.addByteArrays(Response.twoHundred.getBytes(), body);
    }

    @Override
    public byte[] head() {
        return Response.twoHundred.getBytes();
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
