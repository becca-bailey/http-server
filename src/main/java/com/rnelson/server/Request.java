package com.rnelson.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String method;
    private String route;
    private String body;

    public Request(String method, String route) {
        this.method = method;
        this.route = route;
    }

    private Boolean responseHasBody() {
        return method.equals("POST") && route.equals("/echo");
    }

    public String getResponse() {
        Response response = new Response(method, route);
        if (responseHasBody()) {
            response.returnBody(body);
        }
        return response.getHeaderAndBody();
    }

    public void sendBody(String data) {
        this.body = data;
    }
}
