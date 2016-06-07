package com.rnelson.server.unitTests;

import com.rnelson.server.RequestHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestHandlerTests {
    private final String okayResponse = "HTTP/1.1 200 OK\r\n\r\n";
    private final RequestHandler echoHandler = new RequestHandler("GET /echo HTTP/1.1");
    private final RequestHandler headHandler = new RequestHandler("HEAD / HTTP/1.1");
    private final RequestHandler getHandler = new RequestHandler("GET / HTTP/1.1");
    private final RequestHandler postEchoHandler = new RequestHandler("POST /echo?parameter=hello HTTP/1.1");

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
    public void uriReturnsRequestURI() throws Throwable {
        assertEquals("/", getHandler.uri());
        assertEquals("/echo", echoHandler.uri());
        assertEquals("/echo", postEchoHandler.uri());
    }

    @Test
    public void queryStringsReturnsURLParameters() throws Throwable {
        assertEquals("hello", postEchoHandler.queryString());
    }

    @Test
    public void echoResponseEchoesGetRequest() throws Throwable {
        assertEquals("GET /echo HTTP/1.1", echoHandler.getEchoResponse());
        assertEquals("hello", postEchoHandler.getEchoResponse());
    }
}
