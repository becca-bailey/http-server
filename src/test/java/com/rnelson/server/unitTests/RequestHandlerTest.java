package com.rnelson.server.unitTests;

import com.rnelson.server.request.RequestHandler;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestHandlerTest {
    private final RequestHandler echoHandler = new RequestHandler("GET /echo HTTP/1.1\nHost: localhost:8000\n\n");
    private final RequestHandler headHandler = new RequestHandler("HEAD / HTTP/1.1");
    private final RequestHandler getHandler = new RequestHandler("GET / HTTP/1.1");
    private final RequestHandler postEchoHandler = new RequestHandler("POST /echo HTTP/1.1\nHost: localhost:8000\nContent-Length: 5\n\nhello");
    private final RequestHandler notFoundHandler = new RequestHandler("GET /foobar HTTP/1.1");
    private final RequestHandler jpegHandler = new RequestHandler("GET /image.jpeg HTTP/1.1");
    private final RequestHandler pngHandler = new RequestHandler("GET /image.png HTTP/1.1");
    private final RequestHandler parametersHandler = new RequestHandler("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");
    private final RequestHandler testParameters = new RequestHandler("POST /form?my=data");
    private final RequestHandler optionsHandler = new RequestHandler("OPTIONS /method_options");

    @Test
    public void methodReturnsRequestMethod() throws Throwable {
        assertEquals("GET", getHandler.method());
        assertEquals("HEAD", headHandler.method());
    }

    @Test
    public void routeReturnsRequestRoute() throws Throwable {
        assertEquals("/", getHandler.route());
        assertEquals("/echo", echoHandler.route());
        assertEquals("/echo", postEchoHandler.route());
        assertEquals("/parameters", parametersHandler.route());
    }

    @Test
    public void routeReturnsFileRoute() throws Throwable {
        assertEquals("/image.jpeg", jpegHandler.route());
    }

    @Test
    public void routeReturnsRouteWithUnderscores() throws Throwable {
        assertEquals("/method_options", optionsHandler.route());
    }

    @Test
    public void responseBodyReturnsAllLinesAfterNewline() throws Throwable {
        assertEquals("hello", postEchoHandler.getRequestBody());
//        assertEquals("", echoHandler.getRequestBody());
    }

    @Test
    public void parametersReturnsAllURLParameters() throws Throwable {
        assertEquals("variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff", parametersHandler.parameters());
    }
}
