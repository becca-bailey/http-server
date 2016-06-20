package com.rnelson.server.unitTests;

import com.rnelson.file.FileHandler;
import com.rnelson.utilities.SharedUtilities;
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
        String file1Contents = SharedUtilities.convertByteArrayToString(file1.getFileContents());
        String textContents = SharedUtilities.convertByteArrayToString(text.getFileContents());
        assertEquals("file1 contents", file1Contents);
        assertEquals("file1 contents", textContents);
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
}
