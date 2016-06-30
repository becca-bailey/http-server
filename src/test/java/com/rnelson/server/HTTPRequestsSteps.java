package com.rnelson.server;

import application.Config;
import com.rnelson.server.content.Directory;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.httpClient.HTTPClient;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        Directory rootDirectory = new Directory(Config.rootDirectory);
        File form = rootDirectory.getFileByFilename("formData");
        FileHandler handler = new FileHandler(form);
        handler.updateFileContent("");
    }

    // And

    @And("^\"([^\"]*)\" has original contents \"([^\"]*)\"$")
    public void hasOriginalContents(String fileName, String contents) throws Throwable {
        Directory publicDirectory = new Directory(Config.publicDirectory);
        File originalFile = publicDirectory.getFileByFilename(fileName);
        FileHandler handler = new FileHandler(originalFile);
        handler.updateFileContent(contents);
    }

    @And("^I have made additional requests$")
    public void iHaveMadeAdditionalRequests() throws Throwable {
        client.mockRequest("GET", "/log");
        client.mockRequest("PUT", "/these");
        client.mockRequest("HEAD", "/requests");
    }

    // When

    @When("^I request \"([^\"]*)\" \"([^\"]*)\"$")
    public void iRequest(String method, String route) throws Throwable {
        client.sendRequestHeader(method, route);
    }


    @When("^I request \"([^\"]*)\" \"([^\"]*)\" with authorization$")
    public void iRequestWithAuthorization(String method, String route) throws Throwable {
        client.sendRequestHeader(method, route);
        client.sendCredentials("admin", "hunter2");
    }

    @When("^I \"([^\"]*)\" \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iTo(String method, String postBody, String route) throws Throwable {
        client.sendRequestHeader(method, route);
        client.sendRequestBody(postBody);
    }

    // And

    @And("^I specify a range \"([^\"]*)\"$")
    public void iSpecifyARange(String range) throws Throwable {
        client.setRange(range);
    }

    @And("^I set the etag to \"([^\"]*)\"$")
    public void iSetTheEtagTo(String etag) throws Throwable {
        client.setEtag(etag);
    }

    // Then

    @Then("^the response status should be (\\d+)$")
    public void theResponseStatusShouldBe(Integer status) throws Throwable {
        client.connect();
        Integer responseStatus = client.getResponseCode();
        assertEquals(status, responseStatus);
    }

    @Then("^the response body has file contents \"([^\"]*)\"$")
    public void theResponseBodyHasFileContents(String filePath) throws Throwable {
        client.connect();
        byte[] fileContent = Files.readAllBytes(Paths.get(Config.publicDirectory.getPath() + filePath));
        byte[] responseContent = client.getResponseBytes();
        assertArrayEquals(fileContent, responseContent);
    }

    @Then("^the response body should include \"([^\"]*)\"$")
    public void theResponseBodyShouldInclude(String bodyText) throws Throwable {
        client.connect();
        String response = client.getResponseBody();
        assertTrue(response.contains(bodyText));
    }

    // And

    @And("^the response header should include \"([^\"]*)\" \"([^\"]*)\"$")
    public void theResponseHeaderShouldInclude(String fieldName, String property) throws Throwable {
        assertTrue(client.getHeaderField(fieldName).contains(property));
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

    @And("^the body should include partial contents from (\\d+) to (\\d+)$")
    public void theBodyShouldIncludePartialContentsFrom(int rangeStart, int rangeEnd) throws Throwable {
        Directory publicDirectory = new Directory(Config.publicDirectory);
        File partialContentFile = publicDirectory.getFileByFilename("partial_content.txt");
        FileHandler handler = new FileHandler(partialContentFile);
        String fileContent = new String(handler.getFileContents());
        String partialContent = fileContent.substring(rangeStart, rangeEnd + 1);
        String responseContent = client.getResponseBody();
        assertEquals(partialContent, responseContent);
    }

    @And("^the response body has log contents$")
    public void theResponseBodyHasLogContents() throws Throwable {
        String responseBody = client.getResponseBody();
        assertTrue(responseBody.contains("GET /logs HTTP/1.1"));
        assertTrue(responseBody.contains("PUT /these HTTP/1.1"));
        assertTrue(responseBody.contains("HEAD /requests HTTP/1.1"));
    }

    // After

    @After
    public void closeConnection() throws Throwable {
        client.disconnect();
    }

    @And("^the file content is set back to \"([^\"]*)\"$")
    public void theFileContentIsSetBackTo(String defaultContent) throws Throwable {
        Directory publicDirectory = new Directory(Config.publicDirectory);
        File patchFile = publicDirectory.getFileByFilename("patch-content.txt");
        FileHandler patchHandler = new FileHandler(patchFile);
        patchHandler.updateFileContent("");
    }
}