package com.rnelson.server.content;

import application.Config;
import com.rnelson.server.utilities.http.HttpMethods;

import java.io.File;
import java.io.FileNotFoundException;

public class Directory {
    private final File directory;

    public Directory(File directory) {
        this.directory = directory;
    }

    public void addFileRoutes() {
        for (File file : getDirectoryListing()) {
            Config.router.addRoute(HttpMethods.get, "/" + file.getName(), Config.fileController);
        }
    }

    private String generateListItem(String content) {
        return "<li>" + content + "</li>";
    }

    private String generateParagraph(String content) {
        return "<p>" + content + "</p>";
    }

    public String getDirectoryLinks() {
        File[] directoryListing = getDirectoryListing();

        StringBuilder directoryContents = new StringBuilder();
        directoryContents.append(generateParagraph(directory.getName()));
        directoryContents.append("<ul>");
        for (File file : directoryListing) {
            FileHandler fileHandler = new FileHandler(file);
            directoryContents.append(generateListItem(fileHandler.generateFileLink()));
        }
        directoryContents.append("</ul>");
        return directoryContents.toString();
    }

    public File[] getDirectoryListing() {
        return directory.listFiles();
    }

    public File getFileByFilename(String fileName) throws FileNotFoundException {
        File[] directoryListing = getDirectoryListing();
        for (File file : directoryListing) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        throw new FileNotFoundException("File not found in directory");
    }
}
