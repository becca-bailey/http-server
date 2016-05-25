package com.rnelson.server;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ServerSteps {
    int portNumber;

    public static boolean serverIsListening(int port) {
        Socket socket;
        try {
            socket = new Socket("localhost", port);
            socket.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @When("^the server is called with no parameters$")
    public void theServerIsCalledWithNoParameters() throws Throwable {
        String[] args = {};
        portNumber = Server.argsParser(args);
    }

    @Then("^the server will run on port (\\d+)$")
    public void theServerWillRunOnPort(int port) throws Throwable {
        Assert.assertEquals(port, portNumber);
    }

    @When("^the server is called with arguments \"([^\"]*)\" \"([^\"]*)\"$")
    public void theServerIsCalledWithArguments(String parameter, String port) throws Throwable {
        String[] args = {parameter, port};
        portNumber = Server.argsParser(args);
    }

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setErr(null);
    }

    @Then("^the server will display message \"([^\"]*)\"$")
    public void theServerWillDisplayMessage(String message) throws Throwable {
        System.out.println(errContent.toString());
        Assert.assertEquals((message + "\n"), errContent.toString());
    }
}
