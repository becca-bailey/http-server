package com.rnelson.server.unitTests;

import com.rnelson.server.Request;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RequestTests {
    private final String OK = "HTTP/1.1 200 OK";
    private final String NOT_FOUND = "HTTP/1.1 404 NOT FOUND";
    private final String CREATED = "HTTP/1.1 201 CREATED";

    @Test
    public void simpleGET() throws Throwable {
        Request request = new Request("GET", "/");
        String response = request.getResponse();
        assertTrue(response.contains(OK));
    }
}
