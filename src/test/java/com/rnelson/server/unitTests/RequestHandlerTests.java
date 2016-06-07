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
    private final RequestHandler simplePostHandler = new RequestHandler("POST /form?my=data HTTP/1.1");
    private final RequestHandler notFoundHandler = new RequestHandler("GET /foobar HTTP/1.1");

    @Test
    public void getResponseReturnsEcho() throws Throwable {
        assertEquals(okayResponse, echoHandler.getResponse());
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
    public void getResponseReturns404() throws Throwable {
        assertEquals("HTTP/1.1 404 NOT FOUND", notFoundHandler.getResponse());
    }

    @Test
    public void getResponseReturns201ForPOST() throws Throwable {
        assertEquals("HTTP/1.1 201 CREATED", simplePostHandler.getResponse());
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
    public void postResponseReturns201() throws Throwable {
        assertEquals("HTTP/1.1 201 CREATED", simplePostHandler.POSTResponse());
    }
}
