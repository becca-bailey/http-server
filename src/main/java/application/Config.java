package application;


import com.rnelson.server.routing.Router;

import java.io.File;

public class Config {
    public final static String packageName = "application";
    public final static File rootDirectory = new File("src/main/java/application/");
    public final static File publicDirectory = new File("src/main/java/application/public");
    public final static String logfilePath = "src/main/java/application/logs";
    public static Router router;
    public final static String username = "admin";
    public final static String password = "hunter2";
    public final static String fileController = "File";
}
