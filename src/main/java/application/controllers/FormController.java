package application.controllers;

import com.rnelson.server.controller.Controller;

public class FormController extends Controller {
    public byte[] get() {
        return ("This is a form").getBytes();
    }
}
