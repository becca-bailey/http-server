package com.rnelson.server.unitTests;

import com.rnelson.request.Request;
import com.rnelson.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class RequestTest {
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

    @Test
    public void simpleOPTIONS() throws Throwable {
        Request request = new Request("OPTIONS", "/method_options");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(200)));
        assertTrue(response.contains("Allow: GET,HEAD,POST,OPTIONS,PUT"));

        Request request2 = new Request("OPTIONS", "/method_options2");
        String response2 = request2.getResponse();
        assertTrue(response2.contains(Response.status(200)));
        assertTrue(response2.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void simplePUT() throws Throwable {
        Request request = new Request("PUT", "/form");
        request.sendBody("my=data");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void redirectPath() throws Throwable {
        Request request = new Request("GET", "/redirect");
        String response = request.getResponse();
        assertTrue(response.contains(Response.status(302)));
        assertTrue(response.contains("Location: http://localhost:5000"));

    }
}
