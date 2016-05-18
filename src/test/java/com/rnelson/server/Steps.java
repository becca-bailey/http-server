package com.rnelson.server;

import com.rnelson.server.Server;
import cucumber.api.PendingException;
import cucumber.api.java.en.*;


public class Steps {

    @Given("^the server is running on port (\\d+)$")
    public void theServerIsRunningOnPort(int port) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the client connects to the server on port (\\d+)$")
    public void theClientConnectsToTheServerOnPort(int port) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the client inputs a string of text$")
    public void theClientInputsAStringOfText() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the server returns an echo$")
    public void theServerReturnsAnEcho() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
