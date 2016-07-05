package com.rnelson.server.routing;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.request.Credentials;
import com.rnelson.server.utilities.SharedUtilities;
import com.rnelson.server.utilities.http.HttpMethods;
import com.rnelson.server.content.Directory;
import com.rnelson.server.utilities.exceptions.RouterException;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Router {
    private final Set<Route> routes = new HashSet<>();
    private final File rootDirectory;

    public Router (File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void addRoute(String method, String url) {
        Route route = addOrUpdateRoute(url);
        route.addMethod(method);
        routes.add(route);
    }

    public void addRoute(String method, String url, String controllerPrefix) {
        Route route = addOrUpdateRoute(url, controllerPrefix);
        route.addMethod(method);
        routes.add(route);
    }

    public void addProtectedRoute(String method, String url) {
        Route route = addOrUpdateRoute(url);
        route.addMethod(method);
        route.protect();
        routes.add(route);
    }

    private Route addOrUpdateRoute(String url) {
        Route route;
        try {
            route = getExistingRoute(url);
        } catch (RouterException notFound) {
            route = new Route(url);
        }
        return route;
    }

    public void addFileRoutes() {
        Directory directory = new Directory(Config.publicDirectory);
        directory.addFileRoutes();
    }

    private Route addOrUpdateRoute(String url, String controllerPrefix) {
        Route route;
        try {
            route = getExistingRoute(url);
        } catch (RouterException notFound) {
            route = new Route(url, controllerPrefix);
        }
        return route;
    }

    public Route getExistingRoute(String url) throws RouterException {
        if (url.contains("?")) {
            url = url.split("\\?")[0];
        }
        for (Route existingRoute : routes) {
            if (existingRoute.url.equals(url)) {
                return existingRoute;
            }
        }
        throw new RouterException("Route not found. Server is looking for url " + url +".");
    }

    public int countRoutes() {
        return routes.size();
    }

    public File[] listControllers() {
        File controllersDirectory = new File(rootDirectory.getPath() + "/controllers");
        return controllersDirectory.listFiles();
    }

    public Controller getControllerForRequest(Route route) throws RouterException {
        String expectedClassName = expectedControllerClass(route);
        for (File file : listControllers()) {
            FileHandler handler = new FileHandler(file);
            String fileName = handler.removeExtension();
            if (fileName.equals(expectedClassName)) {
                return controllerInstance(fileName);
            }
        }
        throw new RouterException("Controller not found. Server is looking for '/controllers/" + expectedClassName + ".java' in the root directory.");
    }

    public Boolean userIsAuthorized(Route route, Credentials credentials) {
        if (route.isProtected) {
            return checkCredentials(credentials);
        }
        return true;
    }

    private Boolean checkCredentials(Credentials credentials) {
        return credentials.isUsername(Config.username) && credentials.isPassword(Config.password);
    }

    public Supplier<byte[]> getControllerAction(Controller controller, String method) {
        Map<String, Supplier<byte[]>> controllerMethods = new HashMap<>();
        controllerMethods.put(HttpMethods.get, controller::get);
        controllerMethods.put(HttpMethods.head, controller::head);
        controllerMethods.put(HttpMethods.post, controller::post);
        controllerMethods.put(HttpMethods.put, controller::put);
        controllerMethods.put(HttpMethods.options, controller::options);
        controllerMethods.put(HttpMethods.patch, controller::patch);
        controllerMethods.put(HttpMethods.delete, controller::delete);

        return controllerMethods.get(method);
    }

    private String getPackageNameFromFileName(String fileName) {
        return Config.packageName + ".controllers." + SharedUtilities.findMatch("^\\w+", fileName, 0);
    }

    public String expectedControllerClass(Route route){
        String className;
        if (route.controllerPrefix != null) {
            className = route.controllerPrefix;
        } else {
            className = route.getClassName();
        }
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
