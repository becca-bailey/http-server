package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.content.Directory;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;
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
    public void sendRequestBody(String body) {

    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {

    }

    @Override
    public void sendFile(File file) {

    }
}
