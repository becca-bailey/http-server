package com.rnelson.server;

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
