package com.rnelson.request;

import com.rnelson.response.Response;

public class Request {
    private String method;
    private String route;
    private String body;

    public Request(String method, String route) {
        this.method = method;
        this.route = route;
    }

    public byte[] getResponse() {
        Response response = new Response(method, route);
        if (response.echoesBody()) {
            response.sendRequestBody(body);
        }

        byte[] header = response.getHeader();
        byte[] body = response.getBody();
        byte[] responseArray = new byte[header.length + body.length];
        System.arraycopy(header, 0, responseArray, 0, header.length);
        System.arraycopy(body, 0, responseArray, header.length, body.length);

        return responseArray;
    }

    public void sendBody(String data) {
        this.body = data;
    }
}
