package com.rnelson.server.request;

import com.rnelson.server.utilities.http.HttpMethods;

public class Request {
    private String request;

    public Request(String fullRequestFromServer) {
        this.request = fullRequestFromServer;
    }

    public String route() {
        return "/";
    }

    public String method() {
        return HttpMethods.get;
    }
}
