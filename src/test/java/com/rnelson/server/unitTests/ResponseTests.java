package com.rnelson.server.unitTests;

import com.rnelson.server.Response;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ResponseTests {
    @Test
    public void statusCanBeAccessedFromStaticContext() throws Throwable {
        assertEquals(Response.status(200), "HTTP/1.1 200 OK");
        assertEquals(Response.status(0), null);
    }

    @Test
    public void getResponseStatusReturnsStatus() throws Throwable {
        Response response = new Response("GET", "/");
        assertEquals(response.getResponseStatus(), Response.status(200));
    }

    @Test
    public void getOptionsReturnsOptions() throws Throwable {
        Response response = new Response("OPTIONS" ,"/");
        assertEquals(response.getOptions(), "Allow: GET,HEAD");
    }

    @Test
    public void getHeaderReturns404ForInvalidRoute() throws Throwable {
        Response response = new Response("GET", "/foobar");
        assertEquals(Response.status(404) + "\r\n\r\n", response.getHeaderAndBody());
    }

    @Test
    public void getHeaderReturnsHeaderForValidRoute() throws Throwable {
        Response response = new Response("GET", "/");
        assertTrue(response.getHeaderAndBody().contains(Response.status(200)));
    }
}
