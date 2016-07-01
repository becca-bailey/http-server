package com.rnelson.server.serverTests;
import com.rnelson.server.Server;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerTest {

    @Test
    public void isPortParameterReturnsTrueOrFalse() throws Throwable {
        assertTrue(Server.isPortParameter("-p"));
        assertFalse(Server.isPortParameter("-a"));
    }

    @Test
    public void getPortNumberReturnsDefaultPort() throws Throwable {
        String[] args = {};
        Integer defaultPort = 5000;
        assertEquals(defaultPort, Server.getPortNumber(args));
    }

    @Test
    public void getPortNumberReturnsPortArgument() throws Throwable {
        String[] args = {"-p", "8000"};
        Integer port = 8000;
        assertEquals(port, Server.getPortNumber(args));
    }
}
