package com.rnelson.server.serverTests;

import application.Header;
import com.rnelson.server.utilities.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeaderTest {
    private final Header testHeader = new Header(200);

    @Test
    public void getResponseHeaderReturnsFullHeader() {
        assertEquals(Response.status(200) + "\r\n\r\n", new String(testHeader.getResponseHeader()));
    }
}
