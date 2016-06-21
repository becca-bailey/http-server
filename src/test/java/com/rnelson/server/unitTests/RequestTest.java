package com.rnelson.server.unitTests;

import com.rnelson.server.request.Request;
import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.SharedUtilities;
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
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void fourOhFour() throws Throwable {
        Request request = new Request("GET", "/foobar");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(404)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void HEAD200() throws Throwable {
        Request request = new Request("HEAD", "/");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void HEAD404() throws Throwable {
        Request request = new Request("HEAD", "/foobar");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(404)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void GETecho() throws Throwable {
        Request request = new Request("GET", "/echo");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void simplePOST() throws Throwable {
        Request request = new Request("POST", "/form");
        request.sendBody("my=data");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void simpleOPTIONS() throws Throwable {
        Request request = new Request("OPTIONS", "/method_options");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
        assertTrue(response.contains("Allow: GET,HEAD,POST,OPTIONS,PUT"));

        Request request2 = new Request("OPTIONS", "/method_options2");
        byte[] responseBytes2 = request2.getResponse();
        String response2 = new String(responseBytes2, "UTF-8");
        assertTrue(response2.contains(Response.status(200)));
        assertTrue(response2.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void simplePUT() throws Throwable {
        Request request = new Request("PUT", "/form");
        request.sendBody("my=data");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void redirectPath() throws Throwable {
        Request request = new Request("GET", "/redirect");
        byte[] responseBytes = request.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(302)));
        assertTrue(response.contains("Location: http://localhost:5000"));
    }

//    @Test
//    public void parameterDecode() throws Throwable {
//        Request request = new Request("GET", "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");
//        byte[] responseBytes = request.getResponse();
//        String response = SharedUtilities.convertByteArrayToString(responseBytes);
//        assertTrue(response.contains("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all?\""));
//        assertTrue(response.contains("variable_2 = stuff"));
//    }
}
