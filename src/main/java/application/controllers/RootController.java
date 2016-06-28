package application.controllers;

import com.rnelson.server.controller.Controller;
import com.rnelson.server.directory.Directory;
import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.SharedUtilities;

public class RootController extends Controller {

    public byte[] get() {
        Directory directory = new Directory("public/");
        byte[] body =  directory.getDirectoryLinks().getBytes();
        return SharedUtilities.addByteArrays(Response.twoHundred.getBytes(), body);
    }

    public byte[] head() {
        return Response.twoHundred.getBytes();
    }
}
