package application.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.header.Header;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class FileController implements Controller {
    private File file;

    @Override
    public byte[] get() {
        FileHandler handler = new FileHandler(file);
        Header header = new Header(200);
        header.includeContentType(handler.fileExtension());
        return SharedUtilities.addByteArrays(header.getResponseHeader(), handler.getFileContents());
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
    public byte[] redirect() {
        return Response.methodNotAllowed.getBytes();
    }

    @Override
    public void sendRequestData(Map<String, String> data) {

    }

    @Override
    public void sendMethodOptions(Set<String> methodOptions) {

    }

    @Override
    public void sendFile(File file) {
        this.file = file;
    }

    @Override
    public void isAuthorized(Boolean isAuthorized) {

    }
}
