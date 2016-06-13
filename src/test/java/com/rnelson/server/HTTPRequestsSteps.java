package com.rnelson.server;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

import static com.rnelson.server.GlobalHooks.serverRunner;
import static org.junit.Assert.*;

public class HTTPRequestsSteps {
    private HttpURLConnection connection;
    private Integer port;
    private OutputStream out;

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(final int port) throws Throwable {
        this.port = port;
        assertTrue(serverRunner.isRunning());
    }

    @When("^I request \"([^\"]*)\" \"([^\"]*)\"$")
    public void iRequest(String method, String uri) throws Throwable {
        try {
            URL url = new URL("http://localhost:" + port + uri);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @When("^I POST \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iPOSTTo(String postBody, String uri) throws Throwable {
        try {
            URL url = new URL("http://localhost:" + port + uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();

            OutputStream out = connection.getOutputStream();
            out.write(postBody.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("^the response status should be (\\d+)$")
    public void theResponseStatusShouldBe(Integer status) throws Throwable {
        Integer responseStatus = connection.getResponseCode();
        assertEquals(status, responseStatus);
    }

    private String getResponseBody() throws IOException {
        String response = null;
        try {
            InputStream connectionInput = connection.getInputStream();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(connectionInput));
            response = in.readLine();
            connectionInput.close();
            in.close();
        } catch (FileNotFoundException ignored) {
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return response;
    }

    @And("^the response body should be \"([^\"]*)\"$")
    public void theResponseBodyShouldBe(String body) throws Throwable {
        String response = getResponseBody();
        assertEquals(body, response);
    }

    @And("^the response body should be empty$")
    public void theResponseBodyShouldBeEmpty() throws Throwable {
        String response = getResponseBody();
        assertEquals(null, response);
    }

    @After
    public void closeConnection() throws Throwable {
        connection.disconnect();
    }
}