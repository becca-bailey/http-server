package com.rnelson.server.content;

import com.rnelson.server.header.ResponseHeaders;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    private File file;
    private String fileName;
    private String filePath;

    public FileHandler(File file) {
        this.file = file;
        this.fileName = file.getName();
        this.filePath = file.getPath();
    }

    public byte[] getFileContents() {
        byte[] fileContents = new byte[0];
        try {
            fileContents = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }

    public String generateFileLink() {
        return "<a href=\"/" + file.getName() + "\">" + file.getName() + "</a>";
    }

    public String fileExtension() {
        return SharedUtilities.findMatch("([.])(\\w*$)", file.getName(), 2);
    }

    public String fileContentTypeHeader() {
        Map<String, String> fileTypesToContentTypes = new HashMap<String, String>();

        fileTypesToContentTypes.put("jpeg", "image/JPEG");
        fileTypesToContentTypes.put("gif", "image/GIF");
        fileTypesToContentTypes.put("png", "image/PNG");
        fileTypesToContentTypes.put("txt", "text/plain");
        fileTypesToContentTypes.put(null, "text/plain");

        String contentType = fileTypesToContentTypes.get(fileExtension());

        return "Content-Type: " + fileTypesToContentTypes.get(fileExtension());
    }

    public String contentLengthHeader() {
        return "Content-Length: " + ((int)file.length());
    }

    public void addFileContentToPageContent() {
        BodyContent.pageContent.put("/" + fileName, getFileContents());
    }

    public void addRequiredHeaderRowsForFile() {
        ResponseHeaders.requiredHeaderRows.put("GET /" + fileName, Arrays.asList(fileContentTypeHeader()));
    }

    public void updateFileContent(String newContent) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFileContent(String newContent) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}