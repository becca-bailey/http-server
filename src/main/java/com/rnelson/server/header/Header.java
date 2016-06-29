package com.rnelson.server.header;

import com.rnelson.server.utilities.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Header {
    String status;
    String crlf = "\r\n";
    List<String> rows = new ArrayList<String>();

    public Header(int status) {
        this.status = Response.status(status);
    }

    public void includeOptions(Set<String> methodOptions) {
        String optionsHeader = "Allow: " + String.join(",", methodOptions);
        this.rows.add(optionsHeader);
    }

    public void includeContentType(String contentType) {
        String contentTypeHeader = "Content-Type: " + contentType;
        this.rows.add(contentTypeHeader);
    }

    private String getResponseAsString() {
        StringBuilder response = new StringBuilder();
        response.append(status);
        if (rows.size() > 0) {
            response.append(crlf);
            response.append(String.join(",", rows));
        }
        response.append(crlf);
        response.append(crlf);
        return response.toString();
    }

    public byte[] getResponseHeader() {
        return getResponseAsString().getBytes();
    }
}
