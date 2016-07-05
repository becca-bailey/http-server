package application.controllers;

import application.Header;
import com.rnelson.server.utilities.SharedUtilities;

public class CoffeeController extends AbstractController {

    @Override
    public byte[] get() {
        byte[] body = ("I'm a teapot").getBytes();
        Header header = new Header(418);
        byte[] responseHeader = header.getResponseHeader();
        return SharedUtilities.addByteArrays(responseHeader, body);
    }

}

