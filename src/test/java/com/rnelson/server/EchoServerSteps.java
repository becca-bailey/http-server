package com.rnelson.server;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;

import static org.junit.Assert.assertEquals;

public class EchoServerSteps {
    private Socket clientSocket;
    private OutputStream out;
    private BufferedReader in;
    private String request;
    private String actualResponse;

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        Thread server = new Thread(new ServerRunner(5000));
        server.start();
    }

    @When("^I \"([^\"]*)\"$")
    public void i(String request) throws Throwable {
        this.request = request;
        clientSocket = new Socket("localhost", 5000);
        out = clientSocket.getOutputStream();
        out.write(request.getBytes());
    }

    @Then("^the response status should be \"([^\"]*)\"$")
    public void theResponseStatusShouldBe(String status) throws Throwable {
        String okayStatus = "HTTP/1.1 200 OK";
        String expectedResponse = (okayStatus + "\r\n\r\n" + request);

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            actualResponse += inputLine;

        assertEquals(expectedResponse, actualResponse);
    }

//    @After
//    public void closeAllSockets() throws IOException {
//        in.close();
//        out.close();
//        clientSocket.close();
//    }


    @And("^the response body should be empty$")
    public void theResponseBodyShouldBeEmpty() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}