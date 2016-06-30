package com.rnelson.server.request;

import application.Config;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> getRequestData() {
        RequestData data = new RequestData();
        if (hasParameters()) {
            Parameters parameters = new Parameters(getParameters());
            data.addData(parameters.getDecodedParameters());
        }
        if (hasBody()) {
            data.setBody(getRequestBody());
        }
        return data.returnAllData();
    }

    public String getParameters() {
        return url().split("\\?")[1];
    }

    public Boolean hasParameters() {
        return request.contains("?");
    }

    public Boolean hasBody() {
        return getRequestBody().length() > 0;
    }


    public Map<String, String> parseHeaders() {
        Map<String,String> headerFields = new HashMap<String, String>();
        String[] headerLines = splitHeader();
        for (int i = 1; i < headerLines.length; i++) {
            String[] fields = headerLines[i].split(":");
            String parameter = fields[0];
            String options = fields[1].trim();
            headerFields.put(parameter, options);
        }
        return headerFields;
    }

    public Credentials getCredentials () {
        Map<String, String> headerFields = parseHeaders();
        String authorization;
        if (headerFields.containsKey("Authorization")) {
            authorization = headerFields.get("Authorization");
        } else {
            authorization = "";
        }
        return new Credentials(authorization);
    }

    private String getRequestLine() {
        return splitHeader()[0];
    }

    private String[] splitHeader() {
        return header.split("\\n");
    }

    public void logRequest() {
        String requestLine = getRequestLine();
        FileHandler logHandler = new FileHandler(Config.logfile);
        logHandler.addFileContent(requestLine + "\n");
    }
}
