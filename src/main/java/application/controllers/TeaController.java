package application.controllers;

import application.Header;

public class TeaController extends AbstractController {

    @Override
    public byte[] get() {
        Header header = new Header(200);
        return header.getResponseHeader();
    }
}
