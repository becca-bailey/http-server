package com.rnelson.server;

import java.io.*;
import java.net.*;
import java.sql.Time;

class ServerRunner implements Runnable {
    private final int serverPort;
    private Boolean running = true;

    ServerRunner(int port) {
        this.serverPort = port;
    }

    private String getFullRequest(BufferedReader in) throws IOException {
        return in.readLine();
//        StringBuilder request = new StringBuilder();
//        String line;
//        System.out.println(in.ready());
//        request.append(in.readLine());
//        while(in.ready()) {
//            line = in.readLine();
//            request.append(line);
//            request.append("\n");
//        }
//        return request.toString();
    }

    private void respondToRequest (OutputStreamWriter out, BufferedReader in) throws IOException {
        String request = getFullRequest(in);
        System.out.println(request);
        RequestHandler handler = new RequestHandler(request);
        out.write(handler.getResponse());
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
            try (
                    ServerSocket serverSocket = new ServerSocket(serverPort);
                    Socket clientSocket = serverSocket.accept();

                    OutputStreamWriter out =
                            new OutputStreamWriter(clientSocket.getOutputStream());
                    BufferedReader in =
                            new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                respondToRequest(out, in);
                serverSocket.close();
                clientSocket.close();
            } catch (BindException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
