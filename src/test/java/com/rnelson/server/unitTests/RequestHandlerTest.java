package com.rnelson.server.unitTests;

import com.rnelson.request.RequestHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestHandlerTest {
    private final RequestHandler echoHandler = new RequestHandler("GET /echo HTTP/1.1\nHost: localhost:8000\n\n");
    private final RequestHandler headHandler = new RequestHandler("HEAD / HTTP/1.1");
    private final RequestHandler getHandler = new RequestHandler("GET / HTTP/1.1");
    private final RequestHandler postEchoHandler = new RequestHandler("POST /echo HTTP/1.1\nHost: localhost:8000\nContent-Length: 5\n\nhello");
    private final RequestHandler simplePostHandler = new RequestHandler("POST /form?my=data HTTP/1.1");
    private final RequestHandler notFoundHandler = new RequestHandler("GET /foobar HTTP/1.1");
    private final RequestHandler jpegHandler = new RequestHandler("GET /image.jpeg HTTP/1.1");
    private final RequestHandler pngHandler = new RequestHandler("GET /image.png HTTP/1.1");

    @Test
    public void methodReturnsRequestMethod() throws Throwable {
        assertEquals("GET", getHandler.method());
        assertEquals("HEAD", headHandler.method());
    }

    @Test
    public void uriReturnsRequestURI() throws Throwable {
        assertEquals("/", getHandler.route());
        assertEquals("/echo", echoHandler.route());
        assertEquals("/echo", postEchoHandler.route());
    }

    @Test
    public void responseBodyReturnsResponseBodyAsString() throws Throwable {
        assertEquals("hello", postEchoHandler.getRequestBody());
    }

    @Test
    public void requestIsImageReturnsTrueIfRequestIsImage() throws Throwable {
        assertTrue(jpegHandler.requestIsImage());
        assertTrue(pngHandler.requestIsImage());
    }
}
