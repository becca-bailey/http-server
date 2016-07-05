package com.rnelson.server.routing;

import application.Config;
import com.rnelson.server.utilities.http.HttpMethods;

public class RouteInitializer {

    public void initializeRoutes() {
        Router router = new Router(Config.rootDirectory);
        Config.router = router;

        router.addRoute(HttpMethods.get, "/");
        router.addRoute(HttpMethods.head, "/");
        router.addRoute(HttpMethods.get, "/coffee");
        router.addRoute(HttpMethods.get, "/tea");
        router.addRoute(HttpMethods.post, "/form");
        router.addRoute(HttpMethods.get, "/echo");
        router.addRoute(HttpMethods.post, "/echo");
        router.addRoute(HttpMethods.get, "/redirect");
        router.addRoute(HttpMethods.get, "/parameters");

        router.addRoute(HttpMethods.get, "/method_options", "MethodOptions");
        router.addRoute(HttpMethods.head, "/method_options", "MethodOptions");
        router.addRoute(HttpMethods.post, "/method_options", "MethodOptions");
        router.addRoute(HttpMethods.options, "/method_options", "MethodOptions");
        router.addRoute(HttpMethods.put, "/method_options", "MethodOptions");

        router.addRoute(HttpMethods.get, "/method_options2", "MethodOptions");
        router.addRoute(HttpMethods.options, "/method_options2", "MethodOptions");

        router.addProtectedRoute(HttpMethods.get, "/logs");

        router.addFileRoutes();
    }
}
