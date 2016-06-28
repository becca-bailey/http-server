package com.rnelson.server.unitTests;

import com.rnelson.server.request.RequestHandler;
import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.SharedUtilities;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestHandlerTest {
    private final RequestHandler GETecho = new RequestHandler("GET /echo HTTP/1.1\r\n\r\n");
    private final RequestHandler HEAD200 = new RequestHandler("HEAD / HTTP/1.1\r\n\r\n");
    private final RequestHandler HEAD404 = new RequestHandler("HEAD /foobar HTTP/1.1\r\n\r\n");
    private final RequestHandler simpleGET = new RequestHandler("GET / HTTP/1.1\r\n\r\n");
    private final RequestHandler POSTecho = new RequestHandler("POST /echo HTTP/1.1\r\nHost: localhost:8000\r\nContent-Length: 5\r\n\r\nhello");
    private final RequestHandler simplePOST = new RequestHandler("POST /form HTTP/1.1\r\nContent-Length: 7\r\n\r\nmy=data");
    private final RequestHandler simplePUT = new RequestHandler("PUT /form HTTP/1.1\nContent-Length: 7\n\nmy=data");
    private final RequestHandler NotFound = new RequestHandler("GET /foobar HTTP/1.1\r\n\r\n");
    private final RequestHandler jpeg = new RequestHandler("GET /image.jpeg HTTP/1.1\r\n\r\n");
    private final RequestHandler png = new RequestHandler("GET /image.png HTTP/1.1\r\n\r\n");
    private final RequestHandler parameterDecode = new RequestHandler("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff\r\n\r\n");
    private final RequestHandler parameterDecode2 = new RequestHandler("POST /form?my=data\r\n\r\n");
    private final RequestHandler simpleOPTIONS = new RequestHandler("OPTIONS /method_options\r\n\r\n");
    private final RequestHandler simpleOPTIONS2 = new RequestHandler("OPTIONS /method_options2\r\n\r\n");
    private final RequestHandler redirectPath = new RequestHandler("GET /redirect\r\n\r\n");
    private final RequestHandler basicAuth = new RequestHandler("GET /logs\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\n\r\n");


    @Test
    public void methodReturnsRequestMethod() throws Throwable {
        assertEquals("GET", simpleGET.method());
        assertEquals("HEAD", HEAD200.method());
    }

    @Test
    public void routeReturnsRequestRoute() throws Throwable {
        assertEquals("/", simpleGET.route());
        assertEquals("/echo", GETecho.route());
        assertEquals("/parameters", parameterDecode.route());
    }

    @Test
    public void routeReturnsFileRoute() throws Throwable {
        assertEquals("/image.jpeg", jpeg.route());
    }

    @Test
    public void routeReturnsRouteWithUnderscores() throws Throwable {
        assertEquals("/method_options", simpleOPTIONS.route());
    }

    @Test
    public void responseBodyReturnsAllLinesAfterNewline() throws Throwable {
        assertEquals("hello", POSTecho.getRequestBody());
        assertEquals("", GETecho.getRequestBody());
    }

    @Test
    public void parametersReturnsAllURLParameters() throws Throwable {
        assertEquals("variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff", parameterDecode.parameters());
    }

    @Test
    public void headerReturnsOnlyHeaderLines() throws Throwable {
        assertEquals("POST /echo HTTP/1.1\r\nHost: localhost:8000\r\nContent-Length: 5", POSTecho.headerOnly());
        assertEquals("GET /echo HTTP/1.1", GETecho.headerOnly());
    }

    @Test
    public void parseHeadersAddsHeaderFieldsToMap() throws Throwable {
        POSTecho.parseHeaders();
        assertEquals(POSTecho.headerFields.get("Content-Length"), "5");
    }

    // getResponse

    private String getResponseBody(String response) {
        try {
            String[] headerAndBody = response.split("\r\n\r\n");
            return headerAndBody[1];
        } catch (Exception e) {
            return "";
        }
    }

    private Boolean bodyIsEmpty(String response) {
        return getResponseBody(response).equals("");
    }

    @Test
    public void simpleGET() throws Throwable {
        byte[] responseBytes = simpleGET.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void NotFound() throws Throwable {
        byte[] responseBytes = NotFound.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(404)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void HEAD200() throws Throwable {
        byte[] responseBytes = HEAD200.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void HEAD404() throws Throwable {
        byte[] responseBytes = HEAD404.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(404)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void GETecho() throws Throwable {
        byte[] responseBytes = GETecho.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void simplePOST() throws Throwable {
        byte[] responseBytes = simplePOST.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(200)));
        assertTrue(bodyIsEmpty(response));
    }

    @Test
    public void simpleOPTIONS() throws Throwable {
        byte[] responseBytes = simpleOPTIONS.getResponse();
        String response = new String(responseBytes, "UTF-8");
        assertTrue(response.contains(Response.status(200)));
        assertTrue(response.contains("Allow: GET,HEAD,POST,OPTIONS,PUT"));

        byte[] responseBytes2 = simpleOPTIONS2.getResponse();
        String response2 = SharedUtilities.convertByteArrayToString(responseBytes2);
        assertTrue(response2.contains(Response.status(200)));
        assertTrue(response2.contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void simplePUT() throws Throwable {
        byte[] responseBytes = simplePUT.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(200)));
    }

    @Test
    public void redirectPath() throws Throwable {
        byte[] responseBytes = redirectPath.getResponse();
        String response = SharedUtilities.convertByteArrayToString(responseBytes);
        assertTrue(response.contains(Response.status(302)));
        assertTrue(response.contains("Location: http://localhost:5000"));
    }

//    @Test
//    public void parameterDecode() throws Throwable {
//        byte[] responseBytes = parameterDecode.getResponse();
//        String response = SharedUtilities.convertByteArrayToString(responseBytes);
//        assertTrue(response.contains("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
//        assertTrue(response.contains("variable_2 = stuff"));
//    }

    @Test
    public void getUserNameAndPasswordAcceptsBase64EncodedCredentials() {
        String encodedCredentials = "Basic YWRtaW46aHVudGVyMQ==";
        String[] usernameAndPassword = new String[]{"admin", "hunter1"};
        assertEquals(usernameAndPassword[0], basicAuth.getUsernameAndPassword(encodedCredentials)[0]);
        assertEquals(usernameAndPassword[1], basicAuth.getUsernameAndPassword(encodedCredentials)[1]);
    }

    @Test
    public void isAuthorizedReturnsTrueOrFalse() {
        assertTrue(basicAuth.isAuthorized());
    }

    @Test
    public void userIsAuthorizedReturnsTrueOrFalse() {
        assertTrue(basicAuth.userIsAuthorized("admin", "hunter2"));
        assertFalse(basicAuth.userIsAuthorized("test", "test"));
    }
}
