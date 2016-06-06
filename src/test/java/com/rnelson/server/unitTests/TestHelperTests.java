package com.rnelson.server.unitTests;

import com.rnelson.server.HTTPRequestsSteps;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestHelperTests {
    @Test
    public void getResponseBodyReturnsEmptyBody() throws Throwable {
        String emptyBodyResponse = "HTTP/1.1 200 OK\r\n\r\n";
        assertEquals("", HTTPRequestsSteps.getResponseBody(emptyBodyResponse));
    }

    @Test
    public void getResponseBodyReturnsBody() throws Throwable {
        String echoBodyResponse = "HTTP/1.1 200 OK\r\n\r\nhello";
        assertEquals("hello", HTTPRequestsSteps.getResponseBody(echoBodyResponse));
    }
}
