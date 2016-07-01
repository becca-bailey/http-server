package com.rnelson.server.applicationTests;

import application.Config;
import application.controllers.RootController;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.content.Directory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RootControllerTest {
    private final RootController controller = new RootController();

    @Test
    public void getReturnsDirectoryContents() throws Throwable {
        Directory directory = new Directory(Config.publicDirectory);
        String links = directory.getDirectoryLinks();
        String data = new String(controller.get());
        assertTrue(data.contains(links));
    }

    @Test
    public void headReturns200() throws Throwable {
        String data = new String(controller.head());
        assertTrue(data.contains(Response.status(200)));
    }
}
