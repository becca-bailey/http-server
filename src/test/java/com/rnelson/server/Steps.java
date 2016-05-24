package com.rnelson.server;

import cucumber.api.java.en.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class Steps {
    public Socket clientSocket;
    public String hostName;
    public PrintWriter out;
    public BufferedReader in;

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        Thread server = new Thread(new ServerRunner(port));
        server.start();
    }

    @And("^the client connects on port (\\d+)$")
    public void theClientConnectsOnPort(int port) throws Throwable {
        hostName = "localhost";
        try {
            clientSocket = new Socket(hostName, port);
        }
        catch (UnknownHostException e) {
            System.exit(1);
        }
    }

    @When("^the user inputs \"([^\"]*)\"$")
    public void theUserInputs(String userInput) throws Throwable {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        out.println(userInput);
    }

    @Then("^the response is \"([^\"]*)\"$")
    public void theResponseIs(String expectedResponse) throws Throwable {
        String response = in.readLine();
        assertEquals(expectedResponse, response);
    }
}