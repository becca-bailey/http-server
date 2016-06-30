package com.rnelson.server.content;

import com.rnelson.server.utilities.SharedUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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