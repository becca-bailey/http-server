package com.rnelson.server;

import application.Config;
import com.rnelson.server.request.Request;
import com.rnelson.server.request.RequestHandler;
import com.rnelson.server.utilities.exceptions.ControllerException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
        if (requestData.contains(" / ")) {
            Controller controller = null;
            Request request = new Request(requestData);
            String route = request.route();
            String method = request.method();
            try {
                controller = router.getControllerForRoute(route);
            } catch (ControllerException e) {
                System.out.println(e.getMessage());
            } finally {
                response = controller.getResponse(method);
            }
        } else {
            RequestHandler handler = new RequestHandler(requestData);
            response = handler.getResponse();
        }
        out.write(response);
        out.close();
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
