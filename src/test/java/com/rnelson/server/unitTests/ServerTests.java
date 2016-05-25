package com.rnelson.server.unitTests;
import com.rnelson.server.Server;

import org.junit.Assert;
import org.junit.Test;

public class ServerTests {

    @Test
    public void isPortParameterReturnsTrueOrFalse() throws Throwable {
        Assert.assertTrue(Server.isPortParameter("-p"));
        Assert.assertFalse(Server.isPortParameter("-a"));
    }

    @Test
    public void getPortNumberReturnsDefaultPort() throws Throwable {
        String[] args = {};
        Integer defaultPort = 5000;
        Assert.assertEquals(defaultPort, Server.getPortNumber(args));
    }

    @Test
    public void getPortNumberReturnsPortArgument() throws Throwable {
        String[] args = {"-p", "8000"};
        Integer port = 8000;
        Assert.assertEquals(port, Server.getPortNumber(args));
    }
}
