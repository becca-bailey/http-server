package com.rnelson.server.applicationTests;

import application.controllers.CoffeeController;
import com.rnelson.server.utilities.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CoffeeControllerTest {
    private final CoffeeController controller = new CoffeeController();

    @Test
    public void getReturns418() throws Throwable {
        String response = new String(controller.get());
        assertTrue(response.contains(Response.status(418)));
    }

    @Test
    public void getReturnsBodyText() throws Throwable {
        String response = new String(controller.get());
        assertTrue(response.contains("I'm a teapot"));
    }
}
