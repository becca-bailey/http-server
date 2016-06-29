package com.rnelson.server;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.Route;
import com.rnelson.server.utilities.exceptions.RouterException;
import com.rnelson.server.utilities.http.HttpMethods;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Router {
    Set<Route> routes = new HashSet<Route>();
    File rootDirectory;

    public Router (File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void addRoute(String method, String url) {
        Route route = null;
        try {
            route = getExistingRoute(url);
        } catch(RouterException notFound) {
            route = new Route(url);
        } finally {
            route.addMethod(method);
            routes.add(route);
        }
    }

    public Route getExistingRoute(String url) throws RouterException {
        for (Route existingRoute : routes) {
            if (existingRoute.url.equals(url)) {
                return existingRoute;
            }
        }
        throw new RouterException("Route not found. Server is looking for uri " + url +".");
    }

    public int countRoutes() {
        return routes.size();
    }

    public File[] listControllers() {
        File controllersDirectory = new File(rootDirectory.getPath() + "/controllers");
        return controllersDirectory.listFiles();
    }

    public Controller getControllerForRoute(String url) throws RouterException {
        try {
            Route route = getExistingRoute(url);
            String expectedClassName = expectedControllerClass(route.getClassName());
            for (File file : listControllers()) {
                String fileName = FilenameUtils.removeExtension(file.getName());
                if (fileName.equals(expectedClassName)) {
                    return controllerInstance(fileName);
                }
            }
        } catch (RouterException e) {
            System.out.println(e.getMessage());
        }
        throw new RouterException("Controller not found. Server is looking for '/controllers/<This>Controller.java' in the root directory.");
    }

    public Supplier<byte[]> getControllerAction(Controller controller, String method) {
        Map<String, Supplier<byte[]>> controllerMethods = new HashMap<>();
        controllerMethods.put(HttpMethods.get, controller::get);
        controllerMethods.put(HttpMethods.head, controller::head);
        controllerMethods.put(HttpMethods.post, controller::post);
        controllerMethods.put(HttpMethods.put, controller::put);
        controllerMethods.put(HttpMethods.options, controller::options);
        controllerMethods.put(HttpMethods.patch, controller::patch);

        return controllerMethods.get(method);
    }

    private String getPackageNameFromFileName(String fileName) {
        return Config.packageName + ".controllers." + FilenameUtils.removeExtension(fileName);
    }

    private String expectedControllerClass(String className) {
        return className + "Controller";
    }

    private Controller controllerInstance(String fileName) {
        Controller controller = null;
        try {
            Class controllerClass = Class.forName(getPackageNameFromFileName(fileName));
            controller = (Controller) controllerClass.newInstance();
        } catch (ClassCastException e) {
            System.out.println(fileName + " must be an instance of Controller.");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return controller;
    }
}
