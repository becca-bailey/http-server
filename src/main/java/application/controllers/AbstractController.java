package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.utilities.Response;

public abstract class AbstractController implements Controller {
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();
    }
}
