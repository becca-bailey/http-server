package com.rnelson.server;

import application.Config;
import com.rnelson.server.utilities.exceptions.RouterException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
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
        String requestData = getFullRequest(in);
        Config.initializeRoutes();
        byte[] response;
        Controller controller = null;
        Request request = new Request(requestData);
        String uri = request.uri();
        String method = request.method();
        String body = request.getRequestBody();
        try {
            controller = Config.router.getControllerForRoute(uri);
            controller.sendRequestBody(body);
            Supplier<byte[]> controllerAction = Config.router.getControllerAction(controller, method);
            response = getResponse(controllerAction);
        } catch (RouterException e) {
            System.out.println(e.getMessage());
            response = Response.notFound.getBytes();
        } catch (NullPointerException e) {
            System.out.println(method + " not found in Controller.");
            response = Response.methodNotAllowed.getBytes();
        }
        out.write(response);
        out.close();
    }

    private byte[] getResponse(Supplier<byte[]> supplier) {
        return (byte[]) supplier.get();
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

                    respondToRequest(out, in);
                    clientSocket.close();
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
