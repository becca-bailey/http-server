package com.rnelson.server.applicationTests;

import application.controllers.FormController;
import com.rnelson.server.ResponseData;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormControllerTest {
    private final FormController controller = new FormController();
    private final ResponseData responseData = new ResponseData()
;
    private String getData() {
        return new String(controller.get());
    }

    private void sendRequestBody(String body) {
        responseData.sendRequestBody(body);
        controller.sendResponseData(responseData);
    }

    @Test
    public void getFormReturnsFormContentsAfterPost() throws Throwable {
        sendRequestBody("post data");
        controller.post();
        String data = getData();
        assertTrue(data.contains("post data"));
        controller.delete();
    }

    @Test
    public void deleteDeletesFormData() throws Throwable {
        sendRequestBody("post data");
        controller.post();
        controller.delete();
        String data = getData();
        assertFalse(data.contains("post data"));
    }

    @Test
    public void postAddsDataToPage() throws Throwable {
        sendRequestBody("first line\n");
        controller.post();
        sendRequestBody("second line\n");
        controller.post();
        String data = getData();
        assertTrue(data.contains("first line"));
        assertTrue(data.contains("second line"));
        controller.delete();
    }

    @Test
    public void putUpdatesExistingContent() throws Throwable {
        sendRequestBody("first line\n");
        controller.put();
        sendRequestBody("second line\n");
        controller.put();
        String data = getData();
        assertFalse(data.contains("first line"));
        assertTrue(data.contains("second line"));
        controller.delete();
    }
}
