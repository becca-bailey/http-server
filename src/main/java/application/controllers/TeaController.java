package application.controllers;

import com.rnelson.server.controller.Controller;
import com.rnelson.server.header.Header;

public class TeaController extends Controller {
    public byte[] get() {
        Header header = new Header(200);
        return header.getResponseHeader();
    }
}
