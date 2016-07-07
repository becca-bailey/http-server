package com.rnelson.server;

import com.rnelson.server.request.Credentials;
import com.rnelson.server.request.Request;
import com.rnelson.server.routing.Route;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class ResponseData {

    public String requestBody;
    public Map<String,String> parameters;
    public Set<String> methodOptions;
    public File requestedFile;
    public Boolean isAuthorized;
    public String requestedRange;

    public ResponseData() {

    }

    public ResponseData(Request request, Route route) {
        Credentials credentials = request.getCredentials();
        Boolean isAuthorized = ServerConfig.router.userIsAuthorized(route, credentials);
        sendRequestBody(request.getRequestBody());
        sendParameters(request.getDecodedParameters());
        sendMethodOptions(route.getMethods());
        sendFile(route.getFile(ServerConfig.publicDirectory.getPath()));
        setRange(request.getRange());
        requestIsAuthorized(isAuthorized);
    }

    public void sendRequestBody(String body) {
        this.requestBody = body;
    }

    public void sendParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void sendMethodOptions(Set<String> methods) {
        this.methodOptions = methods;
    }

    public void sendFile(File file) {
        this.requestedFile = file;
    }

    public void requestIsAuthorized(Boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    private void setRange(String requestedRange) {
        this.requestedRange = requestedRange;
    }
}
