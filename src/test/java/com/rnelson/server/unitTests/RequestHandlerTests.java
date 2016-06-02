package com.rnelson.server.unitTests;

import com.rnelson.server.RequestHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestHandlerTests {
    private String okayResponse = "HTTP/1.1 200 OK\r\n\r\n";
    private RequestHandler echoHandler = new RequestHandler("GET /echo HTTP/1.1");
    private RequestHandler headHandler = new RequestHandler("HEAD / HTTP/1.1");
    private RequestHandler getHandler = new RequestHandler("GET / HTTP/1.1");

    @Test
    public void getResponseReturnsEcho() throws Throwable {
        assertEquals(okayResponse + "GET /echo HTTP/1.1", echoHandler.getResponse());
    }

    @Test
    public void getResponseReturnsHeadRequest() throws Throwable {
        assertEquals(okayResponse, headHandler.getResponse());
    }

    @Test
    public void getResponseReturnsSimpleGet() throws Throwable {
        assertEquals(okayResponse, getHandler.getResponse());
    }

    @Test
    public void methodReturnsRequestMethod() throws Throwable {
        assertEquals("GET", getHandler.method());
        assertEquals("HEAD", headHandler.method());
    }

    @Test
    public void parameterReturnsRequestParameter() throws Throwable {
        assertEquals("/", getHandler.parameter());
        assertEquals("/echo", echoHandler.parameter());
    }
}
