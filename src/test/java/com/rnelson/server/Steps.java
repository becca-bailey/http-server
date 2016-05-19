package com.rnelson.server;

import com.rnelson.server.Server;

import java.io.*;
import cucumber.api.*;
import cucumber.api.java.*;
import cucumber.api.java.en.*;
import static org.junit.Assert.assertEquals;

public class Steps {
    private ByteArrayOutputStream outputFromServer = new ByteArrayOutputStream();

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        Thread server = new Thread(new Server());
        server.start();
    }

    @And("^the client connects to the server on port (\\d+)$")
    public void theClientConnectsToTheServerOnPort(int port) throws Throwable {
        Client client = new Client();
        Client.connect("localhost", 5000);
    }

    @When("^the user inputs \"([^\"]*)\"$")
    public void theUserInputsAStringOfText(String userInput) throws Throwable {
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
    }

    @Before
    public void getOutStream() {
        System.setOut(new PrintStream(outputFromServer));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Then("^the server echos \"([^\"]*)\"$")
    public void theServerReturnsAnEcho(String userInput) throws Throwable {
        assertEquals(("Echo: " + userInput + "\n"), outputFromServer.toString());
    }
}
