package application.controllers;

import com.rnelson.server.Controller;

public class FormController extends Controller {
    public byte[] get() {
        return ("This is a form").getBytes();
    }
}
