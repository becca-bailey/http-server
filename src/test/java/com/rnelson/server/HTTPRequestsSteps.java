package com.rnelson.server;

import com.rnelson.utilities.Router;
import cucumber.api.java.After;
import cucumber.api.java.en.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.rnelson.server.GlobalHooks.serverRunner;
import static org.junit.Assert.*;

public class HTTPRequestsSteps {
    private Integer port = 5000;
    private HttpURLConnection connection;
    private OutputStream out;

    @Given("^the server is running")
    public void theServerIsRunning() throws Throwable {
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

    @When("^I \"([^\"]*)\" \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iTo(String method, String postBody, String uri) throws Throwable {
        try {
            URL url = new URL("http://localhost:" + port + uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(method);


            Thread.sleep(1000);
            // ^ this solves the problem locally, but not on Travis
            OutputStream out = connection.getOutputStream();
            out.write(postBody.getBytes());
            out.flush();
            out.close();
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("^the response status should be (\\d+)$")
    public void theResponseStatusShouldBe(Integer status) throws Throwable {
        Integer responseStatus = connection.getResponseCode();
        assertEquals(status, responseStatus);
    }

    @And("^the response header should include \"([^\"]*)\" \"([^\"]*)\"$")
    public void theResponseHeaderShouldInclude(String fieldName, String property) throws Throwable {
        assertEquals(property, connection.getHeaderField(fieldName));
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
        } catch (IOException e) {
            if (e.getMessage().contains("418")) {
                response = connection.getResponseMessage();
            } else {
                e.printStackTrace();
            }
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

    @Given("^the page content is empty$")
    public void thePageContentIsEmpty() throws Throwable {
        byte[] emptyContent = new byte[0];
        Router.pageContent.put("/form", emptyContent);
    }

    @And("^the response body has directory contents \"([^\"]*)\"$")
    public void theResponseBodyHasDirectoryContents(String directory) throws Throwable {
        String response = getResponseBody();
        assertTrue(response.contains(directory));
    }

    @And("^the response body has directory link \"([^\"]*)\"$")
    public void theResponseBodyHasDirectoryLink(String filePath) throws Throwable {
        String response = getResponseBody();
        assertTrue(response.contains("a href="));
        assertTrue(response.contains(filePath));
    }

    @Then("^the response body has file contents \"([^\"]*)\"$")
    public void theResponseBodyHasFileContents(String filePath) throws Throwable {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get("public" + filePath));
            String content = new String(fileContent, "UTF-8");
            String response = getResponseBody();
            assertTrue(content.contains(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}