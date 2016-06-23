package com.rnelson.server.file;

import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.Router;

import java.io.*;
import java.util.Arrays;

public class DirectoryHandler {
    private String directory = "public";

    public DirectoryHandler(String directory) {
        this.directory = directory + "/";
    }

    public void handleAllFiles() {
        for (File file : getDirectoryListing()) {
            Router.statusCodesForRoutes.put("GET /" + file.getName(), Response.status(200));
            Router.routeOptions.put("/" + file.getName(), Arrays.asList("GET"));
            FileHandler handler = new FileHandler(file);
            handler.addFileContentToPageContent();
            handler.addRequiredHeaderRowsForFile();
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
        directoryContents.append(generateParagraph(directory));
        directoryContents.append("<ul>");
        for (File file : directoryListing) {
            FileHandler fileHandler = new FileHandler(file);
            directoryContents.append(generateListItem(fileHandler.generateFileLink()));
        }
        directoryContents.append("</ul>");
        return directoryContents.toString();
    }

    private File[] getDirectoryListing() {
        File directory = new File("public/");
        return directory.listFiles();
    }
}
