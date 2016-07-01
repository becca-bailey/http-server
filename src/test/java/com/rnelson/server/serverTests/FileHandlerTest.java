package com.rnelson.server.serverTests;

import com.rnelson.server.content.FileHandler;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHandlerTest {
    private final FileHandler file1 = new FileHandler(new File("src/main/java/application/public/file1"));
    private final FileHandler jpeg = new FileHandler(new File("src/main/java/application/public/image.jpeg"));
    private final FileHandler gif = new FileHandler(new File("src/main/java/application/public/image.gif"));
    private final FileHandler png = new FileHandler(new File("src/main/java/application/public/image.png"));
    private final FileHandler text = new FileHandler(new File("src/main/java/application/public/text-file.txt"));
    private final FileHandler test = new FileHandler(new File("src/test/java/com/rnelson/server/serverTests/testFile"));

    @Test
    public void getFileContentsReturnsContentsOfFile() throws Throwable {
        String file1Contents = new String(file1.getFileContents());
        String textContents = new String(text.getFileContents());
        assertEquals("file1 contents", file1Contents);
        assertEquals("file1 contents", textContents);
    }

    @Test
    public void fileExtensionReturnsFileExtension() throws Throwable {
        assertEquals("jpeg", jpeg.fileExtension());
        assertEquals("gif", gif.fileExtension());
        assertEquals("png", png.fileExtension());
        assertEquals("", file1.fileExtension());
    }

    @Test
    public void generateFileLinkReturnsHTMLLink() throws Throwable {
        assertEquals("<a href=\"/file1\">file1</a>", file1.generateFileLink());
    }

    @Test
    public void updateFileContentReplacesExistingContent() throws Throwable {
        String content = "new content";
        test.updateFileContent(content);
        assertTrue(new String(test.getFileContents()).equals(content));
    }

    @Test
    public void addFileContentAddsToExistingContent() throws Throwable {
        test.addFileContent("add\n");
        test.addFileContent("this\n");
        test.addFileContent("content\n");
        String contents = new String(test.getFileContents());
        assertTrue(contents.contains("add"));
        assertTrue(contents.contains("this"));
        assertTrue(contents.contains("content"));
    }

    @Test
    public void removeExtensionReturnsFilenameWithoutExtension() throws Throwable {
        assertEquals("image", jpeg.removeExtension());
        assertEquals("RootController", FileHandler.removeExtension("RootController.java"));
    }
}
