package application.controllers;

import application.Config;
import com.rnelson.server.content.Directory;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

public class RootController extends AbstractController {

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
}
