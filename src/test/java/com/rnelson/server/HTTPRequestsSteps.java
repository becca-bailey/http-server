package com.rnelson.server;

import cucumber.api.java.After;
import cucumber.api.java.en.*;
import java.io.*;
import java.net.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HTTPRequestsSteps {
    private HttpURLConnection connection;
    private Integer port;

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(final int port) throws Throwable {
        this.port = port;
        Thread server = new Thread(new ServerRunner(port));
        server.start();
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

    @Then("^the response status should be (\\d+)$")
    public void theResponseStatusShouldBe(Integer status) throws Throwable {
        Integer responseStatus = connection.getResponseCode();
        assertEquals(status, responseStatus);
    }

    private String getFullResponse(BufferedReader in) throws Throwable {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }

    @And("^the response body should be empty$")
    public void theResponseBodyShouldBeEmpty() throws Throwable {
        InputStream connectionInput = connection.getInputStream();
        BufferedReader in =
                new BufferedReader(new InputStreamReader(connectionInput));
        String response = getFullResponse(in);
        assertFalse(response.contains("<body>"));

        in.close();
        connectionInput.close();
        connection.disconnect();
    }
}