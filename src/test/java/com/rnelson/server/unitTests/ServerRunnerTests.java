package com.rnelson.server.unitTests;

import com.rnelson.server.ServerRunner;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerRunnerTests {
    ServerRunner server = new ServerRunner(5000);

    @Test
    public void getResponseReturnsEcho() throws Throwable {
        String request = "this is a request";
        assertEquals("Echo: this is a request", server.echoResponse(request));
    }
}
