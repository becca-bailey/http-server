package com.rnelson.server.serverTests;

import application.Config;
import com.rnelson.server.content.Directory;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryTest {
    private final File publicDir = Config.publicDirectory;
    private final Directory directory = new Directory(publicDir);

    @Test
    public void directoryCanBeInstantiatedWithAFile() throws Throwable {
        assertEquals(Directory.class, directory.getClass());
    }

    @Test
    public void getDirectoryLinksReturnsDirectoryContentsAsString() throws Throwable {
        assertTrue(directory.getDirectoryLinks().contains("public"));
    }

    @Test
    public void getFileByFilenameReturnsFileInDirectory() throws Throwable {
        File image = new File("src/main/java/application/public/image.gif");
        assertEquals(image, directory.getFileByFilename("image.gif"));
    }

    @Test
    public void getFileByFilenameThrowsFileNotFoundException() throws Throwable {
        Boolean fileNotFound = false;
        try {
            directory.getFileByFilename("foo");
        } catch (FileNotFoundException e) {
            fileNotFound = true;
        }
        assertTrue(fileNotFound);
    }
}