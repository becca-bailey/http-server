package application.controllers;

import application.Range;
import com.rnelson.server.Controller;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.FileHandler;
import application.Header;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;

public class FileController implements Controller {
    private File file;
    private String requestBody;
    private String requestedRange;

    @Override
    public byte[] get() {
        FileHandler handler = new FileHandler(file);
        if (requestedRange != "") {
            Range range = new Range(requestedRange);
            Header header = new Header(206);
            header.includeContentType(handler.fileExtension());
            byte[] partialFileContent = range.applyRange(handler.getFileContents());
            return SharedUtilities.addByteArrays(header.getResponseHeader(), partialFileContent);
        } else {
            Header header = new Header(200);
            header.includeContentType(handler.fileExtension());
            return SharedUtilities.addByteArrays(header.getResponseHeader(), handler.getFileContents());
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
        FileHandler handler = new FileHandler(file);
        handler.updateFileContent(requestBody);
        Header header = new Header(204);
        return header.getResponseHeader();
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
        this.requestBody = responseData.requestBody;
        this.file = responseData.requestedFile;
        this.requestedRange = responseData.requestedRange;
    }
}
