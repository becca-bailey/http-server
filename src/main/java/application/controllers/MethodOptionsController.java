package application.controllers;

import application.Header;
import com.rnelson.server.ResponseData;

import java.util.Set;

public class MethodOptionsController extends AbstractController {
    private Set<String> methodOptions;

    @Override
    public byte[] options() {
        Header header = new Header(200);
        header.includeOptions(methodOptions);
        return header.getResponseHeader();
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.methodOptions = responseData.methodOptions;
    }
}
