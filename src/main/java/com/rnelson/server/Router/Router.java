package com.rnelson.server.router;

import application.Config;
import com.rnelson.server.route.Route;
import com.rnelson.server.controller.Controller;
import com.rnelson.server.utilities.exceptions.RouterException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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
        throw new RouterException("Route not found. Server is looking for route " + url +".");
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
