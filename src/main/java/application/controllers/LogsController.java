package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.FileHandler;
import application.Header;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;

public class LogsController implements Controller {
    private Boolean isAuthorized;

    @Override
    public byte[] get() {
        Header header;
        if (isAuthorized) {
            header = new Header(200);
            byte[] responseHeader = header.getResponseHeader();
            File logs = new File(Config.rootDirectory + "/views/logs");
            FileHandler handler = new FileHandler(logs);
            byte[] content = handler.getFileContents();
            return SharedUtilities.addByteArrays(responseHeader, content);
        } else {
            header = new Header(401);
            header.includeBasicAuthorization();
            return header.getResponseHeader();
        }
    }

    @Override
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] post() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] put() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] patch() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] options() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] delete() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.isAuthorized = responseData.isAuthorized;
    }
}
