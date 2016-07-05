package application.controllers;

import application.Header;

public class RedirectController extends AbstractController {
    @Override
    public byte[] get() {
        Header header = new Header(302);
        header.includeLocation("http://localhost:5000/");
        return header.getResponseHeader();
    }
}
