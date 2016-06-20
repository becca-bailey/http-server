package com.rnelson.request;

import com.rnelson.response.Response;
import com.rnelson.utilities.SharedUtilities;

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
        return SharedUtilities.addByteArrays(header, body);
    }

    public void sendBody(String data) {
        this.body = data;
    }
}
