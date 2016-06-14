package com.rnelson.server.unitTests;

import com.rnelson.server.Request;
import com.rnelson.server.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class RequestTests {
    private String getResponseBody(String response) {
        try {
            String[] headerAndBody = response.split("\r\n\r\n");
            return headerAndBody[1];
        } catch (Exception e) {
            return "";
        }
    }

    private Boolean bodyIsEmpty(String response) {
        return getResponseBody(response).equals("");
    }

    @Test
    public void simpleGET() throws Throwable {
        Request request = new Request("GET", "/");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void fourOhFour() throws Throwable {
        Request request = new Request("GET", "/foobar");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(404)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void HEAD200() throws Throwable {
        Request request = new Request("HEAD", "/");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void HEAD404() throws Throwable {
        Request request = new Request("HEAD", "/foobar");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(404)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void GETecho() throws Throwable {
        Request request = new Request("GET", "/echo");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void simplePOST() throws Throwable {
        Request request = new Request("POST", "/form");
        request.sendBody("my=data");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }
}
