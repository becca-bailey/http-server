package com.rnelson.server;

import com.rnelson.server.controllers.RootController;
import com.rnelson.server.utilities.exceptions.ControllerException;
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
        throw new RouterException("route not found");
    }

    public int countRoutes() {
        return routes.size();
    }

    public File[] listControllers() {
        File controllersDirectory = new File(rootDirectory.getPath() + "/controllers");
        return controllersDirectory.listFiles();
    }

    public Controller getControllerForRoute(String url) throws ControllerException {
        try {
            Route route = getExistingRoute(url);
            File[] controllerFiles = listControllers();
            String expectedClassName = expectedControllerClass(route.getClassName());
            for (File file : controllerFiles) {
                String fileName = FilenameUtils.removeExtension(file.getName());
                if (controllerMatchesFilename(fileName, expectedClassName)) {
                    Class controllerClass = Class.forName(getPackageNameFromFileName(fileName));
//                    return controllerClass.newInstance();
                    return new RootController();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ControllerException("Controller not found. Server is looking for '/controllers/<This>Controller.java' in the root directory.");
    }

    private String getPackageNameFromFileName(String fileName) {
        return "com.rnelson.server.controllers." + FilenameUtils.removeExtension(fileName);
        // fix this with a config file
    }

    private String expectedControllerClass(String className) {
        return className + "Controller";
    }

    private Boolean controllerMatchesFilename (String fileName, String expectedClassName) {
        return fileName.equals(expectedClassName);
    }
}
