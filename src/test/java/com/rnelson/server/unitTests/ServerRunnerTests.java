package com.rnelson.server.unitTests;

import com.rnelson.server.ServerRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerRunnerTests {
    ServerRunner server = new ServerRunner(5000);
    String okayResponse = "HTTP/1.1 200 OK\r\n\r\n";

    @Test
    public void getResponseReturnsEcho() throws Throwable {
        assertEquals(okayResponse + "GET /echo HTTP/1.1", server.getResponse("GET /echo HTTP/1.1"));
    }

    @Test
    public void getResponseReturnsHeadRequest() throws Throwable {
        assertEquals(okayResponse, server.getResponse("HEAD / HTTP/1.1"));
    }

    @Test
    public void getResponseReturnsSimpleGet() throws Throwable {
        assertEquals(okayResponse, server.getResponse("GET / HTTP/1.1"));
    }
}
