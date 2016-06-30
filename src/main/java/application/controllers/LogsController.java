package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.FileHandler;
import application.Header;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;

public class LogsController implements Controller {
    Boolean isAuthorized;

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
        return new byte[0];
    }

    @Override
    public byte[] post() {
        return new byte[0];
    }

    @Override
    public byte[] put() {
        return new byte[0];
    }

    @Override
    public byte[] patch() {
        return new byte[0];
    }

    @Override
    public byte[] options() {
        return new byte[0];
    }

    @Override
    public byte[] delete() {
        return new byte[0];
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.isAuthorized = responseData.isAuthorized;
    }
}
