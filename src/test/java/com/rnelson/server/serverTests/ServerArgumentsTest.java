package com.rnelson.server.serverTests;

import com.rnelson.server.ServerArguments;
import com.rnelson.server.utilities.exceptions.ServerException;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerArgumentsTest {
    private final String root = "src/main/java/application";

    @Test
    public void rootDirectoryIsARequiredArgument() throws Throwable {
        String[] args = new String[] {};
        Boolean exceptionThrown = false;
        ServerArguments arguments = new ServerArguments(args);
        try {
            arguments.getRootDirectory();
        } catch (ServerException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void portDefaultsTo500IfNoneGiven() throws Throwable {
        String[] args = new String[] {"-r", root};
        ServerArguments arguments = new ServerArguments(args);
        int port = arguments.getPortNumber();
        assertEquals(5000, port);
    }

    @Test
    public void getRootDirectoryReturnsDirectoryAsFile() throws Throwable {
        String[] args = new String[] {"-r", root};
        ServerArguments arguments = new ServerArguments(args);
        File rootDirectory = arguments.getRootDirectory();
        assertEquals(root, rootDirectory.getPath());
    }

    @Test
    public void getPortNumberReturnsPortNumberIfPortIsSpecified() throws Throwable {
        String[] args = new String[] {"-p", "8000", "-r", root};
        ServerArguments arguments = new ServerArguments(args);
        assertEquals(8000, arguments.getPortNumber());
    }
}
