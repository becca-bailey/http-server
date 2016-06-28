package com.rnelson.server.header;

import com.rnelson.server.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeaderTest {
    private Header testHeader = new Header(200);

    @Test
    public void getResponseHeaderReturnsFullHeader() {
        assertEquals(Response.status(200) + "\r\n\r\n", new String(testHeader.getResponseHeader()));
    }
}
