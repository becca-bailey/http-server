package com.rnelson.server;

import com.rnelson.server.request.Request;
import com.rnelson.server.routing.Route;
import com.rnelson.server.routing.RouteInitializer;
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
        RouteInitializer initializer = new RouteInitializer();
        initializer.initializeRoutes();
        // this initializes the router
        byte[] response;
        Request request = new Request(getFullRequest(in));
        try {
            loadProperties();
            Route route = ServerConfig.router.getExistingRoute(request.url());
            Controller controller = ServerConfig.router.getControllerForRequest(route);
            ResponseData responseData = new ResponseData(request, route);
            controller.sendResponseData(responseData);
            Supplier<byte[]> controllerAction = ServerConfig.router.getControllerAction(controller, request.method());
            response = getResponse(controllerAction);
        } catch (RouterException e) {
            System.err.println(e.getMessage());
            response = Response.notFound.getBytes();
        }
        out.write(response);
        out.close();
    }

    private void loadProperties() throws IOException {
        Properties config = new Properties();
        String filename = "config.properties";
        InputStream input = new FileInputStream("src/main/java/com/rnelson/server" + "/config.properties");
        if (input != null) {
            config.load(input);
            System.out.println(config.getProperty("packageName"));
            ServerConfig.packageName = config.getProperty("packageName");
            input.close();
        } else {
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
