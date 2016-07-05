package application.controllers;

import application.Header;
import com.rnelson.server.ResponseData;
import com.rnelson.server.utilities.SharedUtilities;

import java.util.Map;

public class ParametersController extends AbstractController {
    private Map<String,String> parameters;

    @Override
    public byte[] get() {
        Header header = new Header(200);
        StringBuilder text = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            text.append(entry.getKey());
            text.append(" = ");
            text.append(entry.getValue());
            text.append("\n");
        }
        byte[] parametersInBody = text.toString().getBytes();
        return SharedUtilities.addByteArrays(header.getResponseHeader(), parametersInBody);
    }

    @Override
    public void sendResponseData(ResponseData responseData) {
        this.parameters = responseData.parameters;
    }
}
