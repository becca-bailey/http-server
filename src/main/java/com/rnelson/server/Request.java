package com.rnelson.server;

public class Request {
    private String method;
    private String uri;

    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public String getResponse() {
        return Response.status(200);
    }
}
