package com.rnelson.server.unitTests;

import com.rnelson.server.FileHandler;
import com.rnelson.server.Router;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class FileHandlerTest {
    private FileHandler file1 = new FileHandler(new File("public/file1"));
    private FileHandler jpeg = new FileHandler(new File("public/image.jpeg"));
    private FileHandler gif = new FileHandler(new File("public/image.gif"));
    private FileHandler png = new FileHandler(new File("public/image.png"));
    private FileHandler text = new FileHandler(new File("public/text-file.txt"));

    @Test
    public void getFileContentsReturnsContentsOfFile() throws Throwable {
        assertEquals("file1 contents", file1.getFileContents());
        assertEquals("file1 contents", text.getFileContents());
    }

    @Test
    public void fileContentTypeHeaderReturnsContentTypeHeader() throws Throwable {
        assertEquals("Content-Type: image/JPEG", jpeg.fileContentTypeHeader());
        assertEquals("Content-Type: image/GIF", gif.fileContentTypeHeader());
        assertEquals("Content-Type: image/PNG", png.fileContentTypeHeader());
        assertEquals("Content-Type: text/plain", text.fileContentTypeHeader());
    }

    @Test
    public void fileContentTypeReturnsFileExtension() throws Throwable {
        assertEquals("jpeg", jpeg.fileExtension());
        assertEquals("gif", gif.fileExtension());
        assertEquals("png", png.fileExtension());
        assertEquals(null, file1.fileExtension());
    }

//    @Test
//    public void isImageFileReturnsTrueForImageFileElseFalse() throws Throwable {
//        assertTrue(Router.fileIsImage(jpeg));
//        assertTrue(Router.fileIsImage(gif));
//        assertTrue(Router.fileIsImage(png));
//        assertFalse(Router.fileIsImage(text));
//    }
}
