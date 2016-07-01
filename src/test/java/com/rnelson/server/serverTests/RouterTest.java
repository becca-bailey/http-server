package com.rnelson.server.serverTests;

import com.rnelson.server.routing.Route;
import com.rnelson.server.routing.Router;
import com.rnelson.server.utilities.exceptions.RouterException;
import com.rnelson.server.utilities.http.HttpMethods;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class RouterTest {
    File rootDirectory = new File("src/main/java/com/rnelson/server/");
    Router testRouter = new Router(rootDirectory);

    File testDirectory = new File("applicationTests-directory/");
    Router rootRouter = new Router(testDirectory);
    String root = "/";

    @Test
    public void addRouteAddsNewRouteToCollection() throws Throwable {
        assertEquals(0, testRouter.countRoutes());
        testRouter.addRoute(HttpMethods.get, root);
        assertEquals(1, testRouter.countRoutes());
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

//    @Test
//    public void listControllersReturnsListOfControllersInGivenDirectory() throws Throwable {
//        File firstController = rootRouter.listControllers()[0];
//        assertEquals("RootController.java", firstController.getName());
//    }

//    @Test
//    public void getControllerForRouteReturnsNewControllerInstance() throws Throwable {
//        testRouter.addRoute("GET", "/");
//        Controller controller = testRouter.getControllerForRoute("/");
//        assertEquals(RootController.class, controller.getClass());
//    }

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

//    @Test
//    public void getControllerForRouteThrowsControllerException() throws Throwable {
//        Boolean routerException = false;
//        try {
//            testRouter.getControllerForRoute("/foobar");
//        } catch (RouterException e) {
//            routerException = true;
//        }
//        assertTrue(routerException);
//    }
}
