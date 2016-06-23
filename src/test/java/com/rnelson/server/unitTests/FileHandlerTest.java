package com.rnelson.server.unitTests;

import com.rnelson.server.file.FileHandler;
import com.rnelson.server.response.BodyContent;
import com.rnelson.server.response.ResponseHeaders;
import com.rnelson.server.utilities.SharedUtilities;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

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
    public void fileExtensionReturnsFileExtension() throws Throwable {
        assertEquals("jpeg", jpeg.fileExtension());
        assertEquals("gif", gif.fileExtension());
        assertEquals("png", png.fileExtension());
        assertEquals(null, file1.fileExtension());
    }

    @Test
    public void generateFileLinkReturnsHTMLLink() throws Throwable {
        assertEquals("<a href=\"/file1\">file1</a>", file1.generateFileLink());
    }

    @Test
    public void contentLengthHeaderReturnsHeaderField() throws Throwable {
        assertEquals("Content-Length: 14", text.contentLengthHeader());
    }

    @Test
    public void addFileContentToPageContent() throws Throwable {
        file1.addFileContentToPageContent();
        String fileContent = SharedUtilities.convertByteArrayToString(BodyContent.pageContent.get("/file1"));
        assertEquals("file1 contents", fileContent);
    }

    @Test
    public void addRequiredHeaderRowsForFileAddsHeaderRows() throws Throwable {
        file1.addRequiredHeaderRowsForFile();
        List<String> headerContents = ResponseHeaders.requiredHeaderRows.get("GET /file1");
//        assertTrue(headerContents.contains("Content-Length: 14"));
    }
}
