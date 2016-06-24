package com.rnelson.server.request;

import com.rnelson.server.file.DirectoryHandler;
import com.rnelson.server.response.BodyContent;
import com.rnelson.server.response.ResponseHeaders;
import com.rnelson.server.utilities.Router;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.*;

public class RequestHandler {
    private String method;
    private String route;
    private String body;
    private String request;
    private String[] headerLines;
    private String[] responseParameters;
    public Map<String, String> headerFields;


    public RequestHandler(String request) {
        this.request = request;
        this.headerLines = splitHeader();
        this.method = method();
        this.route = route();
        this.body = getRequestBody();
        Router router = new Router();
    }

    public byte[] getResponse() {
        List<String> arguments = getResponseBodyArguments();
        Map<String, String> headerFields = parseHeaders();
        String parameters = parseParameters();
        preparePageContent();

        BodyContent bodyContent = new BodyContent(method, route);
        bodyContent.sendRequestBody(body);
        bodyContent.sendArguments(arguments);
        bodyContent.sendHeaderFields(headerFields);
        bodyContent.sendUrlParameters(parameters);

        byte[] responseBody = bodyContent.getBody();

        ResponseHeaders headers = new ResponseHeaders(method, route);
        headers.sendArguments(arguments);
        headers.sendBodyLength(responseBody.length);

        byte[] header = headers.getHeader();
        return SharedUtilities.addByteArrays(header, responseBody);
    }

    private String statusLine() {
        String[] headerLines = splitHeader();
        return headerLines[0];
    }

    public String headerOnly() {
        return SharedUtilities.findMatch("(.*)([\\r]*\\n[\\r]*\\n)", request, 1);
    }

    public String[] splitHeader() {
        String header = headerOnly();
        String[] headerLines;
        try {
            headerLines = header.split("\\n");
        } catch (NullPointerException e) {
            headerLines = new String[] {header};
        }
        return headerLines;
    }

    public String method() {
        return SharedUtilities.findMatch("^\\S+", headerLines[0], 0);
    }

    public String route() {
        return SharedUtilities.findMatch("\\/([a-z]|[.|_|-]|\\d)*", headerLines[0], 0);
    }

    public String getRequestBody() {
        return SharedUtilities.findMatch("([\\r]*\\n[\\r]*\\n)(.*)", request, 2);
    }

    public String parameters() {
        return SharedUtilities.findMatch("([?])(.*)", headerLines[0], 2);
    }

    public Map<String, String> parseHeaders() {
        headerFields = new HashMap<String, String>();
        for (int i = 1; i < headerLines.length; i++) {
            String[] fields = headerLines[i].split(":");
            String parameter = fields[0];
            String options = fields[1].trim();
            headerFields.put(parameter, options);
        }
        return headerFields;
    }

    private boolean requiresAuthorization() {
        return Router.protectedRoutes.contains(route);
    };

    public boolean isAuthorized() {
        Map<String, String> headerFields = parseHeaders();
        if (headerFields.containsKey("Authorization")) {
            String encodedCredentials = headerFields.get("Authorization");
            String[] usernameAndPassword = getUsernameAndPassword(encodedCredentials);
            String username = usernameAndPassword[0];
            String password = usernameAndPassword[1];
            return userIsAuthorized(username, password);
        }
        return false;
    }

    public boolean includeUnauthorizedArgument() {
        return requiresAuthorization() || !isAuthorized();
    }

    public String[] getUsernameAndPassword(String encodedCredentials) {
        String[] usernameAndPassword = new String[2];
        if (SharedUtilities.findMatch("Basic", encodedCredentials, 0) != null) {
            String encoding = SharedUtilities.findMatch("\\S+$", encodedCredentials, 0);
            String decoded = decodeBase64(encoding);
            usernameAndPassword = decoded.split(":");
        }
        return usernameAndPassword;
    }

    private String decodeBase64(String encoding) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoding.getBytes());
        return new String(decodedBytes);
    }

    public Boolean userIsAuthorized(String username, String password) {
        return (password.equals(Router.authorizedUsers.get(username)));
    }

    private List<String> getResponseBodyArguments() {
        List<String> arguments = new ArrayList<String>();

        Boolean echoResponseBody = sendEchoResponse();
        Boolean headOnly = sendHeadOnly();
        Boolean delete = method.equals("DELETE");
        Boolean range = headerIncludesRange();
        Boolean authorized = isAuthorized();
        Boolean unauthorized = includeUnauthorizedArgument();

        Map<String, Boolean> argumentAndConditions = new HashMap<String, Boolean>();
        argumentAndConditions.put("echoResponseBody", echoResponseBody);
        argumentAndConditions.put("headOnly", headOnly);
        argumentAndConditions.put("delete", delete);
        argumentAndConditions.put("range", range);
        argumentAndConditions.put("authorized", authorized);
        argumentAndConditions.put("unauthorized", unauthorized);

        for (Map.Entry<String,Boolean> entry : argumentAndConditions.entrySet()) {
            String parameter = entry.getKey();
            Boolean condition = entry.getValue();
            if (condition) {
                arguments.add(parameter);
            }
        }
        return arguments;
    }

    private Boolean sendHeadOnly() {
        List<String> postOnlyMethods = Arrays.asList("HEAD", "POST", "PUT", "OPTIONS");
        return postOnlyMethods.contains(method);
    }

    private Boolean sendEchoResponse() {
        return route.equals("/echo") && ((method.equals("POST") || method.equals("PUT")));
    }

    private Boolean headerIncludesRange() {
        return parseHeaders().containsKey("Range");
    }

    public String parseParameters() {
        String parameterString = "";
        if (parameters() != null) {
            ParameterParser parameters = new ParameterParser(parameters());
            parameterString = parameters.convertToBodyText();
        }
        return parameterString;
    }

    public void preparePageContent() {
        BodyContent.pageContent.put("/coffee", ("I'm a teapot").getBytes());
        String publicDirectory = "public";
        DirectoryHandler directoryHandler = new DirectoryHandler(publicDirectory);
        BodyContent.pageContent.put("/", (directoryHandler.getDirectoryLinks()).getBytes());
        directoryHandler.handleAllFiles();
    }

}
