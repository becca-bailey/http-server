package com.rnelson.server;

public class Server {

    public static Integer setPort(String parameter, String port) {
        int portNumber = 5000;
        if (parameter.equals("-p")) {
            try {
                portNumber = Integer.parseInt(port);
            } catch (Exception e) {
                System.err.println("Usage: java -jar package/http-server-0.0.1.jar -p <port number>");
            }
        }
        else {
            System.err.println("Usage: java -jar package/http-server-0.0.1.jar -p <port number>");
        }
        return portNumber;
    }

    public static Integer argsParser(String[] args) {
        int portNumber = 5000;
        if (args.length > 1) {
            portNumber = setPort(args[0], args[1]);
        }
        return portNumber;
    }

    public static void main(String[] args) throws Exception {
        int portNumber = argsParser(args);
        ServerRunner runner = new ServerRunner(portNumber);
        runner.run();
    }
}

