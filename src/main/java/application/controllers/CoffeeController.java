package application.controllers;

import com.rnelson.server.controller.Controller;
import com.rnelson.server.header.Header;
import com.rnelson.server.utilities.SharedUtilities;

public class CoffeeController extends Controller {
    public byte[] get() {
        byte[] body = ("I'm a teapot").getBytes();
        Header header = new Header(418);
        byte[] responseHeader = header.getResponseHeader();
        return SharedUtilities.addByteArrays(responseHeader, body);
    }
}

