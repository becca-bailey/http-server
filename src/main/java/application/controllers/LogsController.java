package application.controllers;

import application.Config;
import application.Header;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;

public class LogsController extends AbstractController {
    private Boolean isAuthorized;

    @Override
    public byte[] get() {
        Header header;
        if (isAuthorized) {
            header = new Header(200);
            byte[] responseHeader = header.getResponseHeader();
            File logs = new File(Config.logfilePath);
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
    public void sendResponseData(ResponseData responseData) {
        this.isAuthorized = responseData.isAuthorized;
    }
}
