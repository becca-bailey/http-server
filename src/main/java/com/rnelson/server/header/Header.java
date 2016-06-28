package com.rnelson.server.header;

import com.rnelson.server.response.Response;

public class Header {
    String status;
    String crlf = "\r\n";

    public Header(int status) {
        this.status = Response.status(status);
    }

    public byte[] getResponseHeader() {
        return (status + crlf + crlf).getBytes();
    }
}
