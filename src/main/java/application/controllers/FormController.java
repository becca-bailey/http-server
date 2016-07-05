package application.controllers;

import application.Config;
import application.Header;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.Directory;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.utilities.ContentType;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;
import java.io.FileNotFoundException;

public class FormController extends AbstractController {

    private String body;
    private FileHandler handler;

    private FileHandler getHandler() {
        Directory rootDirectory = new Directory(Config.rootDirectory);
        File formData = null;
        try {
            formData = rootDirectory.getFileByFilename("formData");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new FileHandler(formData);
    }

    @Override
    public byte[] get() {
        Header header = new Header(200);
        byte[] bodyContent;
        handler = getHandler();
        bodyContent = handler.getFileContents();
        return SharedUtilities.addByteArrays(header.getResponseHeader(), bodyContent);
    }

    @Override
    public byte[] post() {
        handler = getHandler();
        handler.addFileContent(body);
        Header header = new Header(200);
        header.includeContentType(ContentType.text);
        return header.getResponseHeader();
    }

    @Override
    public byte[] put() {
        handler = getHandler();
        handler.updateFileContent(body);
        Header header = new Header(200);
        header.includeContentType(ContentType.text);
        return header.getResponseHeader();
    }

    @Override
    public byte[] delete() {
        handler = getHandler();
        handler.updateFileContent("");
        return Response.twoHundred.getBytes();
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.body = responseData.requestBody;
    }
}
