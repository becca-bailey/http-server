package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.file.DirectoryHandler;
import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.SharedUtilities;

public class RootController extends Controller {

    private byte[] twoHundred = (Response.status(200) + "\r\n\r\n").getBytes();

    public byte[] get() {
        DirectoryHandler directory = new DirectoryHandler("public/");
        byte[] body =  directory.getDirectoryLinks().getBytes();
        return SharedUtilities.addByteArrays(twoHundred, body);
    }

    public byte[] head() {
        return twoHundred;
    }
}
