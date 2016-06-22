package com.rnelson.server;

import com.rnelson.server.utilities.Router;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.rnelson.server.GlobalHooks.serverRunner;
import static org.junit.Assert.*;

public class HTTPRequestsSteps {
    private Integer port = 5000;
    private HttpURLConnection connection;
    private OutputStream out;
    private HTTPClient client;

    // Before

    @Before
    public void connectClient(){
        client = new HTTPClient("localhost", port);
    }

    // Given

    @Given("^the server is running")
    public void theServerIsRunning() throws Throwable {
        assertTrue(serverRunner.isRunning());
    }

    @Given("^the page content of \"([^\"]*)\" is empty$")
    public void thePageContentOfIsEmpty(String route) throws Throwable {
        byte[] emptyContent = new byte[0];
        Router.pageContent.put(route, emptyContent);
    }

    // When

    @When("^I request \"([^\"]*)\" \"([^\"]*)\"$")
    public void iRequest(String method, String route) throws Throwable {
        client.sendRequestHeader(method, route);
        client.connect();
//        Thread clientRunner = new Thread(client);
//        clientRunner.run();
    }

    @When("^I \"([^\"]*)\" \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iTo(String method, String postBody, String route) throws Throwable {
        client.sendRequestHeader(method, route);
        client.sendRequestBody(postBody);
        client.connect();

    }

    // Then

    @Then("^the response status should be (\\d+)$")
    public void theResponseStatusShouldBe(Integer status) throws Throwable {
        Integer responseStatus = client.getResponseCode();
        assertEquals(status, responseStatus);
    }

    @Then("^the response body has file contents \"([^\"]*)\"$")
    public void theResponseBodyHasFileContents(String filePath) throws Throwable {
        byte[] fileContent = Files.readAllBytes(Paths.get("public" + filePath));
        byte[] responseContent = client.getResponseBytes();
        assertEquals(fileContent, responseContent);
    }

    // And

    @And("^the response header should include \"([^\"]*)\" \"([^\"]*)\"$")
    public void theResponseHeaderShouldInclude(String fieldName, String property) throws Throwable {
        assertEquals(property, client.getHeaderField(fieldName));
    }

    @And("^the response body should be \"([^\"]*)\"$")
    public void theResponseBodyShouldBe(String body) throws Throwable {
        String responseBody = client.getResponseBody();
        assertEquals(body, responseBody);
    }

    @And("^the response body should be empty$")
    public void theResponseBodyShouldBeEmpty() throws Throwable {
        String responseBody = client.getResponseBody();
        assertEquals("", responseBody);
    }

    @And("^the response body has directory contents \"([^\"]*)\"$")
    public void theResponseBodyHasDirectoryContents(String directory) throws Throwable {
        String responseBody = client.getResponseBody();
        assertTrue(responseBody.contains(directory));
    }

    @And("^the response body has directory link \"([^\"]*)\"$")
    public void theResponseBodyHasDirectoryLink(String filePath) throws Throwable {
        String responseBody = client.getResponseBody();
        assertTrue(responseBody.contains("a href="));
        assertTrue(responseBody.contains(filePath));
    }

    @After
    public void closeConnection() throws Throwable {
        client.disconnect();
    }
}