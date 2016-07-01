package com.rnelson.server.applicationTests;

import application.Header;
import com.rnelson.server.utilities.ContentType;
import com.rnelson.server.utilities.Response;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeaderTest {
    private final Header testHeader = new Header(200);

    private String headerAsString() {
        return new String(testHeader.getResponseHeader());
    }

    @Test
    public void includeOptionsAddsOptionsRow() throws Throwable {
        Set<String> methodOptions = new HashSet<String>();
        methodOptions.add("GET");
        methodOptions.add("POST");
        testHeader.includeOptions(methodOptions);
        assertTrue(headerAsString().contains("Allow: POST,GET"));
    }

    @Test
    public void includeContentTypeAddsContentType() throws Throwable {
        testHeader.includeContentType(ContentType.jpeg);
        assertTrue(headerAsString().contains("Content-Type: image/jpeg"));
    }

    @Test
    public void includeLocationAddsLocation() throws Throwable {
        testHeader.includeLocation("http://localhost:5000/");
        assertTrue(headerAsString().contains("Location: http://localhost:5000/"));
    }

    @Test
    public void includeBasicAuthorizationAddsAuthorization() throws Throwable {
        testHeader.includeBasicAuthorization();
        assertTrue(headerAsString().contains("WWW-Authenticate: Basic"));
    }

    @Test
    public void getResponseHeaderReturnsFullHeader() throws Throwable {
        assertEquals(Response.status(200) + "\r\n\r\n", new String(testHeader.getResponseHeader()));
    }
}
