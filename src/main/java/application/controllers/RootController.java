package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.Directory;
import application.Header;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

public class RootController implements Controller {

    @Override
    public byte[] get() {
        Directory directory = new Directory(Config.publicDirectory);
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
    public void sendResponseData(ResponseData responseData) {

    }

    public byte[] redirect() {
        Header header = new Header(302);
        header.includeLocation("http://localhost:5000");
        return header.getResponseHeader();
    }
}
