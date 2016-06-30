package application;


import com.rnelson.server.routing.Router;
import com.rnelson.server.utilities.http.HttpMethods;

import java.io.File;

public class Config {
    public static String packageName = "application";
    public static File rootDirectory = new File("src/main/java/application/");
    public static File publicDirectory = new File("src/main/java/application/public");
    public static File logfile = new File("src/main/java/application/views/logs");
    public static Router router;
    public static boolean redirect;
    public static String username = "admin";
    public static String password = "hunter2";
    public static String partialContentController = "PartialContent";
    public static String fileController = "File";

    public static void initializeRoutes() {
        router = new Router(Config.rootDirectory);
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
    }
}
