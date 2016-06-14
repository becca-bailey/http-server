package com.rnelson.server.unitTests;

import com.rnelson.server.RequestHandler;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestHandlerTests {
    private final RequestHandler echoHandler = new RequestHandler("GET /echo HTTP/1.1\nHost: localhost:8000\n\n");
    private final RequestHandler headHandler = new RequestHandler("HEAD / HTTP/1.1");
    private final RequestHandler getHandler = new RequestHandler("GET / HTTP/1.1");
    private final RequestHandler postEchoHandler = new RequestHandler("POST /echo HTTP/1.1\nHost: localhost:8000\nContent-Length: 5\n\nhello");
    private final RequestHandler simplePostHandler = new RequestHandler("POST /form?my=data HTTP/1.1");
    private final RequestHandler notFoundHandler = new RequestHandler("GET /foobar HTTP/1.1");

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
    public void responseBodyReturnsResponseBodyAsString() throws Throwable {
        assertEquals("hello", postEchoHandler.getRequestBody());
    }
}
