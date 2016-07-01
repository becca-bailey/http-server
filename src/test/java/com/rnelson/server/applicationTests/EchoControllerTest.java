package com.rnelson.server.applicationTests;

import application.controllers.EchoController;
import com.rnelson.server.ResponseData;
import com.rnelson.server.utilities.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EchoControllerTest {
    private final EchoController controller = new EchoController();

    @Test
    public void getEchoReturns200() throws Throwable {
        String response = new String(controller.get());
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void postEchoReturnsPostBody() throws Throwable {
        ResponseData responseData = new ResponseData();
        responseData.sendRequestBody("post data");
        controller.sendResponseData(responseData);
        String response = new String(controller.post());
        assertTrue(response.contains("post data"));
    }
}

