package com.rnelson.server.applicationTests;

import application.Config;
import application.controllers.MethodOptionsController;
import com.rnelson.server.ResponseData;
import com.rnelson.server.routing.Route;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MethodOptionsTest {
    private final MethodOptionsController controller = new MethodOptionsController();

    @Test
    public void optionsReturnsOptionsForRoute() throws Throwable {
        try {
            Config.initializeRoutes();
            Route options1 = Config.router.getExistingRoute("/method_options");
            Route options2 = Config.router.getExistingRoute("/method_options2");
            ResponseData responseData = new ResponseData();

            responseData.sendMethodOptions(options1.getMethods());
            controller.sendResponseData(responseData);
            String data = new String(controller.options());
            assertTrue(data.contains(String.join(",", options1.getMethods())));

            responseData.sendMethodOptions(options2.getMethods());
            controller.sendResponseData(responseData);
            String data2 = new String(controller.options());
            assertTrue(data2.contains(String.join(",", options2.getMethods())));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
