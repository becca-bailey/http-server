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

    private String fileContentsAsString() throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        return new String(fileContent, "UTF-8");
    }

//    private Boolean fileIsImage() {
//        List<String> imageExtensions = Arrays.asList("jpg, jpeg, png, svg, gif, bmp");
//        if (imageExtensions.contains(fileExtension())) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public String getFileContents() throws IOException {
        return fileContentsAsString();
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

    public void addFileContentToPageContent() throws IOException {
        Router.pageContent.put("/" + fileName, getFileContents());
    }

    public void addRequiredHeaderRowsForFile() {
        Response.requiredHeaderRows.put("GET /" + fileName, Arrays.asList(fileContentTypeHeader()));
    }
}
