package com.rnelson.server;

import com.rnelson.server.utilities.SharedUtilities;

public class Request {
    private String request;
    private String header;
    private String body;

    public Request(String fullRequestFromServer) {
        this.request = fullRequestFromServer;
        header = getRequestHeader();
        body = getRequestBody();
    }

    public String url() {
        return getRequestLine().split(" ")[1];
    }

    public String method() {
        return getRequestLine().split(" ")[0];
    }

    public String getRequestHeader() {
        return SharedUtilities.findMatch("(.*)([\\r]*\\n[\\r]*\\n)", request, 1);
    }

    public String getRequestBody() {
        return SharedUtilities.findMatch("([\\r]*\\n[\\r]*\\n)(.*)", request, 2);
    }

    public String getRequestLine() {
        return splitHeader()[0];
    }

    public String[] splitHeader() {
        return header.split("\\n");
    }

}
