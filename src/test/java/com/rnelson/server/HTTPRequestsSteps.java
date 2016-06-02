package com.rnelson.server;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.*;
import java.io.*;
import java.net.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HTTPRequestsSteps {
    private HttpURLConnection connection;

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        Thread server = new Thread(new ServerRunner(5000));
        server.start();
    }

    @When("^I request \"([^\"]*)\" \"([^\"]*)\"$")
    public void iRequest(String method, String uri) throws Throwable {
        URL url = new URL("http://localhost:5000" + uri);
        connection = (HttpURLConnection)url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.connect();
    }

    @Then("^the response status should be (\\d+)$")
    public void theResponseStatusShouldBe(Integer status) throws Throwable {
        Integer responseStatus = connection.getResponseCode();
        assertEquals(status, responseStatus);
    }

    public String getFullResponse(BufferedReader in) throws Throwable {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }

    @And("^the response body should be empty$")
    public void theResponseBodyShouldBeEmpty() throws Throwable {
        BufferedReader in =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = getFullResponse(in);
        assertFalse(response.contains("<body>"));
    }
}