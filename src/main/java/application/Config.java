package application;


import com.rnelson.server.Router;
import com.rnelson.server.utilities.http.HttpMethods;

import java.io.File;

public class Config {
    public static String rootDirectory = "src/main/java/application";
    public static String packageName = "application";
    public static String publicDirectory = "public/";
    public static Router router;

    public static void initializeRoutes() {
        File root = new File(rootDirectory);
        router = new Router(root);
        router.addRoute(HttpMethods.get, "/");
        router.addRoute(HttpMethods.head, "/");
        router.addRoute(HttpMethods.get, "/coffee");
        router.addRoute(HttpMethods.get, "/tea");
        router.addRoute(HttpMethods.post, "/form");
        router.addRoute(HttpMethods.get, "/echo");
        router.addRoute(HttpMethods.post, "/echo");
        router.addRoute(HttpMethods.options, "/method_options");
        router.addRoute(HttpMethods.options, "/method_options2");
        router.addRoute(HttpMethods.get, "/logs");
    }
}
