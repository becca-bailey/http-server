package application.controllers;

import application.Config;
import application.Header;
import com.rnelson.server.ResponseData;
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


    @Override
    public void sendResponseData(ResponseData responseData) {

    }

    public byte[] redirect() {
        Header header = new Header(302);
        header.includeLocation("http://localhost:5000");
        return header.getResponseHeader();
    }
}
