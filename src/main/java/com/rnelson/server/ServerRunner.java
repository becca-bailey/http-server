package com.rnelson.server;

import com.rnelson.server.request.Request;
import com.rnelson.server.routing.Route;
import com.rnelson.server.routing.RouteInitializer;
import com.rnelson.server.routing.Router;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.exceptions.RouterException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.function.Supplier;

class ServerRunner implements Runnable {
    private final int serverPort;
    private Boolean running = true;
    private final File rootDirectory;

    ServerRunner(int port, File rootDirectory) {
        this.serverPort = port;
        this.rootDirectory = rootDirectory;
    }

    private String getFullRequest(BufferedReader in) throws IOException {
        StringBuilder request = new StringBuilder();
        request.append(in.readLine());
        request.append("\n");
        while(in.ready()) {
            request.append((char) in.read());
        }
        return request.toString();
    }

    private void respondToRequest (DataOutputStream out, BufferedReader in) throws IOException {
        ServerConfig.rootDirectory = rootDirectory;
        byte[] response = new byte[0];
        Request request = new Request(getFullRequest(in));
        try {
            loadProperties();
            ServerConfig.router = new Router(ServerConfig.rootDirectory);
            addRoutes(ServerConfig.router);
            Route route = ServerConfig.router.getExistingRoute(request.url());
            Controller controller = ServerConfig.router.getControllerForRequest(route);
            ResponseData responseData = new ResponseData(request, route);
            controller.sendResponseData(responseData);
            Supplier<byte[]> controllerAction = ServerConfig.router.getControllerAction(controller, request.method());
            response = getResponse(controllerAction);
        } catch (RouterException e) {
            System.err.println(e.getMessage());
            response = Response.notFound.getBytes();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Error loading router. Make sure routesClass is defined in config.properties and implements com.rnelson.server.RouteInitializer");
        }
        out.write(response);
        out.close();
    }

    private void addRoutes(Router router) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class initializerClass = Class.forName(ServerConfig.routesClass);
        RouteInitializer initializer = (RouteInitializer) initializerClass.newInstance();
        initializer.initializeRoutes(router);
    }

    private void loadProperties() throws IOException {
        Properties config = new Properties();
        String filename = "config.properties";
        try {
            InputStream input = new FileInputStream(rootDirectory.getPath() + "/config.properties");
            config.load(input);
            ServerConfig.packageName = config.getProperty("packageName");
            ServerConfig.routesClass = config.getProperty("routesClass");
            input.close();
        } catch (Exception e) {
            System.err.println("Properties not found");
        }
    }

    private byte[] getResponse(Supplier<byte[]> supplier) {
        byte[] response;
        try {
            response = supplier.get();
        } catch (NullPointerException e) {
            System.err.println("Method doesn't exist in Router actions.\n");
            return Response.methodNotAllowed.getBytes();
        }
        return response;
    }

    public void stop() {
        this.running = false;
    }

    public Boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        while (running) {
            try {
                    ServerSocket serverSocket = new ServerSocket(serverPort);
                    Socket clientSocket = serverSocket.accept();
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader in =
                            new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    System.out.println("Server is running on port " + serverPort + "\n");

                    respondToRequest(out, in);
                    clientSocket.close();
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
