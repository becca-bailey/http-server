package com.rnelson.server.serverTests;

import com.rnelson.server.request.Request;
import com.rnelson.server.utilities.http.HttpMethods;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class RequestTest {
    private String crlf = "\r\n";
    private String http = "HTTP/1.1";

    private Request HEAD200 = new Request(requestBuilder("HEAD /"));
    private Request simpleGET = new Request(requestBuilder("GET /"));
    private Request fullRoute = new Request(requestBuilder("GET /this/url"));
    private Request requestBody = new Request(requestBuilder("GET /body", "this is body text"));
    private Request POSTEcho = new Request(requestBuilder("POST /echo", "Content-Length: 5", "hello"));
    private Request parameters = new Request(requestBuilder("GET /parameters?my=parameters"));

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
        assertEquals("/", simpleGET.url());
        assertEquals("/this/url", fullRoute.url());
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

    @Test
    public void parseHeadersAddsHeaderFieldsToMap() throws Throwable {
        Map<String, String> headerFields = POSTEcho.parseHeaders();
        assertEquals(headerFields.get("Content-Length"), "5");
    }

    @Test
    public void hasParametersReturnsTrueIfRequestHasParameters() throws Throwable {
        assertTrue(parameters.hasParameters());
    }

    @Test
    public void hasParametersReturnsFalseIfNoParameters() throws Throwable {
        assertFalse(HEAD200.hasParameters());
    }

    @Test
    public void getEncodedParametersReturnsOnlyUrlParameters() throws Throwable {
        assertEquals("my=parameters", parameters.getEncodedParameters());
    }

    @Test
    public void hasBodyReturnsTrueIfRequestHasBody() throws Throwable {
        assertTrue(requestBody.hasBody());
    }

    @Test
    public void hasBodyReturnsFalseIfNoBody() throws Throwable {
        assertFalse(HEAD200.hasBody());
    }

    @Test
    public void getDecodedParametersReturnsParameters() throws Throwable {
        Map<String, String> data = new HashMap<String,String>();
        data.put("my", "parameters");
        assertEquals(data, parameters.getDecodedParameters());
    }
}
