package com.rnelson.server;

import com.rnelson.server.Server;

import java.io.*;
import java.util.Scanner;

import cucumber.api.*;
import cucumber.api.java.*;
import cucumber.api.java.en.*;

import static java.lang.System.in;
import static org.junit.Assert.assertEquals;

public class Steps {

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        Thread server = new Thread(new ServerRunner(port));
    }

    @And("^the client connects to the server on port (\\d+)$")
    public void theClientConnectsToTheServerOnPort(int port) throws Throwable {
        Thread client = new Thread(new ClientRunner("localhost", port));
    }

    @When("^the user inputs \"([^\"]*)\"$")
    public void theUserInputsAStringOfText(String userInput) throws Throwable {

    }

    @Then("^the server echos \"([^\"]*)\"$")
    public void theServerReturnsAnEcho(String userInput) throws Throwable {

    }
}
