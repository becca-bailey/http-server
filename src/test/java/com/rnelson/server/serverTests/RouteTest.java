package com.rnelson.server.serverTests;

import com.rnelson.server.utilities.http.HttpMethods;
import com.rnelson.server.routing.Route;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouteTest {
    private final Route root = new Route("/form");

    @Test
    public void aRouteCanBeInstantiatedWithAString() throws Throwable {
        assertEquals(root.getClass(), Route.class);
    }

    @Test
    public void addMethodAddsAMethodToList() throws Throwable {
        root.addMethod(HttpMethods.get);
        assertTrue(root.hasMethod(HttpMethods.get));
    }

    @Test
    public void addMethodDoesNotAddDuplicateMethods() throws Throwable {
        root.addMethod(HttpMethods.get);
        root.addMethod(HttpMethods.get);
        assertEquals(1, root.countMethods());
        assertTrue(root.hasMethod(HttpMethods.get));
    }

    @Test
    public void getPathsReturnsAllUrlPaths() throws Throwable {
        Route route = new Route("/get/this/path");
        List<String> pathNames = Arrays.asList("get", "this", "path");
        assertEquals(pathNames, route.getPaths());
    }

    @Test
    public void getClassNameReturnsStringClassname() throws Throwable {
        Route route = new Route("/files/1");
        assertEquals("Files", route.getClassName());
    }

    @Test
    public void getClassNameReturnsRoot() throws Throwable {
        Route route = new Route("/");
        assertEquals("Root", route.getClassName());
    }

    @Test
    public void routeCanBeInitializedWithDefaultRootName() throws Throwable {
        Route route = new Route("/", "Home");
        assertEquals("Home", route.getClassName());
    }
}
