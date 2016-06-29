package application.controllers;

import application.Config;
import com.rnelson.server.Controller;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.header.Header;
import com.rnelson.server.utilities.ContentType;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;
import java.util.Set;

public class FormController implements Controller {

    String body;
    FileHandler handler;

    private FileHandler getHandler() {
        File form = new File(Config.rootDirectory + "/views/form");
        return new FileHandler(form);
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
    public byte[] head() {
        return Response.methodNotAllowed.getBytes();
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
    public byte[] patch() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] options() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public byte[] delete() {
        handler = getHandler();
        handler.updateFileContent("");
        return Response.twoHundred.getBytes();
    }

    @Override
    public void sendRequestBody(String body) {
        this.body = body;
    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {

    }
}
