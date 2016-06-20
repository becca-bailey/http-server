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

    public String getResponse() {
        Response response = new Response(method, route);
        if (method.equals("POST") || method.equals("PUT")) {
            response.sendBody(body);
        }
        return response.getHeaderAndBody();
    }

    public void sendBody(String data) {
        this.body = data;
    }
}
