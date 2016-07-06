package com.rnelson.server.applicationTests;

import application.controllers.LogsController;
import com.rnelson.server.ResponseData;
import com.rnelson.server.utilities.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LogsControllerTest {
    private final LogsController controller = new LogsController();
    ResponseData responseData = new ResponseData();

    @Test
    public void getReturns401IfNotAuthorized() throws Throwable {
        String response = new String(controller.get());
        assertTrue(response.contains(Response.status(401)));
    }

    @Test
    public void getReturnsAuthorizationHeaderIfNotAuthorized() throws Throwable {
        String response = new String(controller.get());
        assertTrue(response.contains("WWW-Authenticate: Basic"));
    }

    @Test
    public void getReturns200IfAuthorized() throws Throwable {
        ResponseData data = new ResponseData();
        data.requestIsAuthorized(true);
        controller.sendResponseData(data);
        String response = new String(controller.get());
        assertTrue(response.contains(Response.status(200)));
    }
}
