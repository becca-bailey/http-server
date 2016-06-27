package com.rnelson.server;

import com.rnelson.server.utilities.exceptions.RouterException;

import java.util.HashSet;
import java.util.Set;

public class Router {
    Set<Route> routes = new HashSet<Route>();

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
}
