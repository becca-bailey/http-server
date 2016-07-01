package com.rnelson.server.applicationTests;

import application.controllers.ParametersController;
import com.rnelson.server.ResponseData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ParametersControllerTest {
    private final ParametersController controller = new ParametersController();
    private final Map<String,String> parameters = new HashMap<>();

    @Test
    public void getReturnsParametersAsString() throws Throwable {
        parameters.put("variable_1", "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?");
        parameters.put("variable_2", "stuff");
        ResponseData responseData = new ResponseData();
        responseData.sendParameters(parameters);
        controller.sendResponseData(responseData);

        String expectedLine1 = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String expectedLine2 = "variable_2 = stuff";
        String response = new String(controller.get());
        assertTrue(response.contains(expectedLine1));
        assertTrue(response.contains(expectedLine2));
    }
}
