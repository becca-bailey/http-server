package com.rnelson.server.request;

import com.rnelson.server.utilities.http.HttpMethods;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    private String crlf = "\r\n";
    private String http = "HTTP/1.1";

    private Request HEAD200 = new Request(requestBuilder("HEAD /"));
    private Request simpleGET = new Request(requestBuilder("GET /"));
    private Request fullRoute = new Request(requestBuilder("GET /this/route"));
    private Request requestBody = new Request(requestBuilder("GET /body", "this is body text"));
    private Request POSTEcho = new Request(requestBuilder("POST /echo", "Content-Length: 5", "hello"));

    private String requestBuilder(String methodAndRoute) {
        return methodAndRoute + " " + http + crlf + crlf;
    }

    private String requestBuilder(String methodAndRoute, String body) {
        return methodAndRoute + " " + http + crlf + crlf + body;
    }

    private String requestBuilder(String methodAndRoute, String headerRows, String body) {
        return methodAndRoute + " " + http + crlf + headerRows + crlf + crlf + body;
    }

    @Test
    public void methodReturnsRequestMethod() throws Throwable {
        assertEquals(HttpMethods.get, simpleGET.method());
        assertEquals(HttpMethods.head, HEAD200.method());
    }

    @Test
    public void routeReturnsFullRoute() throws Throwable {
        assertEquals("/", simpleGET.route());
        assertEquals("/this/route", fullRoute.route());
    }

    @Test
    public void getRequestBodyReturnsAllLinesAfterNewline() throws Throwable {
        assertEquals("this is body text", requestBody.getRequestBody());
        assertEquals("", simpleGET.getRequestBody());
    }

    @Test
    public void getRequestHeaderReturnsAllHeaderRows() throws Throwable {
        assertEquals("POST /echo HTTP/1.1\r\nContent-Length: 5", POSTEcho.getRequestHeader());
    }
}
