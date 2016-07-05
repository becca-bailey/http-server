package com.rnelson.server.serverTests;

import application.Config;
import application.controllers.RootController;
import com.rnelson.server.Controller;
import com.rnelson.server.routing.Route;
import com.rnelson.server.routing.Router;
import com.rnelson.server.utilities.exceptions.RouterException;
import com.rnelson.server.utilities.http.HttpMethods;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class RouterTest {
    private final File rootDirectory = Config.rootDirectory;
    private final Router testRouter = new Router(rootDirectory);
    private final String root = "/";

    @Test
    public void addRouteAddsNewRouteToCollection() throws Throwable {
        assertEquals(0, testRouter.countRoutes());
        testRouter.addRoute(HttpMethods.get, root);
        assertEquals(1, testRouter.countRoutes());
    }

    @Test
    public void addRouteAllowsSpecifiedControllerPrefix() throws Throwable {
        testRouter.addRoute(HttpMethods.get, "/kitty", "Cat");
        Route kitty = testRouter.getExistingRoute("/kitty");
        assertEquals("Cat", kitty.controllerPrefix);
    }

    @Test
    public void expectedControllerClassReturnsDefaultOrSpecifiedClassName() throws Throwable {
        testRouter.addRoute(HttpMethods.post, "/coffee");
        Route coffee = testRouter.getExistingRoute("/coffee");
        String controllerName = testRouter.expectedControllerClass(coffee);
        assertEquals("CoffeeController", controllerName);

        testRouter.addRoute(HttpMethods.post, "/coffee2", "Tea");
        Route coffee2 = testRouter.getExistingRoute("/coffee2");
        String myControllerName= testRouter.expectedControllerClass(coffee2);
        assertEquals("TeaController", myControllerName);
    }

    @Test
    public void addRouteAddsNewMethodToExistingRoute() throws Throwable {
        testRouter.addRoute(HttpMethods.get, root);
        testRouter.addRoute(HttpMethods.head, root);
        assertEquals(1, testRouter.countRoutes());

        Route route = testRouter.getExistingRoute(root);
        assertTrue(route.hasMethod(HttpMethods.get));
        assertTrue(route.hasMethod(HttpMethods.head));
    }

    @Test
    public void listControllersReturnsListOfControllersInGivenDirectory() throws Throwable {
        File[] controllers = testRouter.listControllers();
        for (File controller : controllers) {
            assertTrue(controller.getName().contains("Controller"));
        }
    }

    @Test
    public void getControllerForRouteReturnsNewControllerInstance() throws Throwable {
        testRouter.addRoute("GET", "/");
        Route route = testRouter.getExistingRoute("/");
        Controller controller = testRouter.getControllerForRequest(route);
        assertEquals(RootController.class, controller.getClass());
    }

    @Test
    public void getExistingRouteReturnsRoute() throws Throwable {
        testRouter.addRoute("GET", "/");
        assertNotEquals(null, testRouter.getExistingRoute("/"));
    }

    @Test
    public void getExistingRouterThrowsRouterExceptionIfNotFound() throws Throwable {
        Boolean routerException = false;
        try {
            testRouter.getExistingRoute("/foobar");
        } catch (RouterException e) {
            routerException = true;
        }
        assertTrue(routerException);
    }

    @Test
    public void getControllerForRouteThrowsControllerException() throws Throwable {
        Boolean routerException = false;
        Route invalid = new Route("/foobar");
        try {
            testRouter.getControllerForRequest(invalid);
        } catch (RouterException e) {
            routerException = true;
        }
        assertTrue(routerException);
    }
}
