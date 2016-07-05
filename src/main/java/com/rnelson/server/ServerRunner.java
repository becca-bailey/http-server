package com.rnelson.server;

import application.Config;
import com.rnelson.server.request.Credentials;
import com.rnelson.server.request.Request;
import com.rnelson.server.routing.Route;
import com.rnelson.server.routing.Router;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.exceptions.RouterException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.function.Supplier;

class ServerRunner implements Runnable {
    private final int serverPort;
    private Boolean running = true;
    private Router router = Config.router;

    ServerRunner(int port) {
        this.serverPort = port;
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
        Config.initializeRoutes();
        Config.router.addFileRoutes();

        byte[] response;

        Request request = new Request(getFullRequest(in));

        String url = request.url();
        String method = request.method();
        Map<String, String> headerFields = request.parseHeaders();
        Credentials credentials = request.getCredentials();

        try {
            Route route = Config.router.getExistingRoute(url);
            Boolean isAuthorized = Config.router.userIsAuthorized(route, credentials);
            Controller controller = Config.router.getControllerForRequest(route, headerFields);

            ResponseData responseData = new ResponseData(request);
            responseData.sendMethodOptions(route.getMethods());
            responseData.sendFile(route.getFile(Config.publicDirectory.getPath()));
            responseData.requestIsAuthorized(isAuthorized);

            controller.sendResponseData(responseData);
            Supplier<byte[]> controllerAction = Config.router.getControllerAction(controller, method);
            response = getResponse(controllerAction);
        } catch (RouterException e) {
            System.out.println(e.getMessage());
            response = Response.notFound.getBytes();
        }
        out.write(response);
        out.close();
    }

    private byte[] getResponse(Supplier<byte[]> supplier) {
        byte[] response = new byte[0];
        try {
            response = supplier.get();
        } catch (NullPointerException e) {
            System.out.println("Method doesn't exist in Router actions.");
            e.printStackTrace();
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
                    System.out.println("Server is running on port " + serverPort);

                    respondToRequest(out, in);
                    clientSocket.close();
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
