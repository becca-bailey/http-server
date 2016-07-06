package com.rnelson.server.httpClient;

import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.net.URISyntaxException;

public class InvalidRequest extends HttpRequestBase {
    private final ProtocolVersion protocolVersion;
    private URI requestUrl;
    private String method;

    public InvalidRequest(String method, String requestUrl) throws URISyntaxException {
        this.requestUrl = new URI(requestUrl);
        this.method = method;
        this.protocolVersion = new ProtocolVersion("HTTP", 1, 1);
    }

    public String getMethod() {
        return method;
    }

    public URI getURI() {
        return requestUrl;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
