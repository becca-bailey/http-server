package com.rnelson.server.httpClient;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HTTPClientTest {
    private final HTTPClient testClient = new HTTPClient("localhost", 5000);

    @Before
    public void setDefaultVariables() {
        testClient.sendRequestHeader("GET", "/");
        testClient.sendRequestBody("test body");
        testClient.setResponseVariables("HTTP/1.1 200 OK\r\nContent-Length: 4\r\n\r\ntest content");
    }

    @Test
    public void HTTPClientCanBeInitializedWithHostnameAndPort() throws Throwable {
        assertEquals("localhost", testClient.hostName);
        Integer port = 5000;
        assertEquals(port, testClient.portNumber);
    }

    @Test
    public void sendBodySetsBodyText() throws Throwable {
        assertEquals("test body", testClient.body);
    }

    @Test
    public void sendHeaderSetsHeader() throws Throwable {
        assertEquals("GET / HTTP/1.1", testClient.requestLine);
    }

    @Test
    public void getResponseCodeReturnsIntegerCode() throws Throwable {
        Integer status = 200;
        assertEquals(status, testClient.getResponseCode());
    }
}
