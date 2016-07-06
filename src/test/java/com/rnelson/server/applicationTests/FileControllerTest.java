package com.rnelson.server.applicationTests;

import application.Config;
import application.Header;
import application.controllers.FileController;
import com.rnelson.server.ResponseData;
import com.rnelson.server.content.Directory;
import com.rnelson.server.content.FileHandler;
import com.rnelson.server.utilities.Response;
import com.rnelson.server.utilities.SharedUtilities;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class FileControllerTest {
    private final FileController controller = new FileController();
    private final ResponseData responseData = new ResponseData();
    private final Directory directory = new Directory(Config.publicDirectory);

    @Test
    public void getReturnsFileContentsAsByteArray() {
        try {
            File image = directory.getFileByFilename("image.jpeg");
            FileHandler handler = new FileHandler(image);
            responseData.sendFile(image);
            controller.sendResponseData(responseData);
            byte[] imageContents = handler.getFileContents();
            Header header = new Header(200);
            header.includeContentType("jpeg");
            byte[] expectedResponse = SharedUtilities.addByteArrays(header.getResponseHeader(), imageContents);
            assertArrayEquals(expectedResponse, controller.get());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void patchReturns204Response() throws Throwable {
        try {
            File text = directory.getFileByFilename("patch-content.txt");
            responseData.sendFile(text);
            responseData.sendRequestBody("patch content");
            controller.sendResponseData(responseData);
            String response = new String(controller.patch());
            assertTrue(response.contains(Response.status(204)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
