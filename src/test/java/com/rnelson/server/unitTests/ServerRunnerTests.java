package com.rnelson.server.unitTests;

import com.rnelson.server.ServerRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerRunnerTests {
    ServerRunner server = new ServerRunner(5000);

    @Test
    public void getResponseReturnsEcho() throws Throwable {
        assertEquals("HTTP/1.1 200 OK\r\n\r\nGET /echo HTTP/1.1", server.getResponse("GET /echo HTTP/1.1"));
    }

    @Test
    public void getResponseReturnsHeadRequest() throws Throwable {
        assertEquals("HTTP/1.1 200 OK\r\n\r\n", server.getResponse("HEAD / HTTP/1.1"));
    }

}
