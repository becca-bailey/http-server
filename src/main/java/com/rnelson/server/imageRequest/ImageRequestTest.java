package com.rnelson.server.imageRequest;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ImageRequestTest {
    ImageRequest jpegHandler = new ImageRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
    ImageRequest gifHandler = new ImageRequest("GET /image.gif HTTP/1.1\r\n\r\n");

    @Test
    public void getContentTypeReturnsImageType() throws Throwable {
        assertEquals("jpeg", jpegHandler.getContentType());
    }
}
