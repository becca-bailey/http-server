package com.rnelson.server.applicationTests;

import application.controllers.RedirectController;
import com.rnelson.server.utilities.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RedirectControllerTest {
    RedirectController controller = new RedirectController();
    String redirectResponse = new String(controller.get());

    @Test
    public void getReturns302Response() {
        assertTrue(redirectResponse.contains(Response.status(302)));
    }

    @Test
    public void headerIncludesLocation() {
        assertTrue(redirectResponse.contains("Location: http://localhost:5000/"));
    }
}

