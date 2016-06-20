package com.rnelson.file;

import com.rnelson.response.Response;
import com.rnelson.utilities.Router;
import com.rnelson.utilities.SharedUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    public Boolean fileIsImage() {
        if (SharedUtilities.imageExtensions.contains(fileExtension())) {
            return true;
        } else {
            return false;
        }
    }

    public byte[] getFileContents() throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
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

        return "Content-Type: " + fileTypesToContentTypes.get(fileExtension());
    }

    private String contentLengthHeader() {
        return "Content-Length: " + ((int)file.length());
    }

    public void addFileContentToPageContent() throws IOException {
        Router.pageContent.put("/" + fileName, getFileContents());
    }

    public void addRequiredHeaderRowsForFile() {
        Response.requiredHeaderRows.put("GET /" + fileName, Arrays.asList(fileContentTypeHeader(), contentLengthHeader()));
    }
}
