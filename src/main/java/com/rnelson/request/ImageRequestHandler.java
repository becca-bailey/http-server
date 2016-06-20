package com.rnelson.request;

import com.rnelson.file.FileHandler;
import com.rnelson.utilities.SharedUtilities;

import java.io.File;
import java.net.MalformedURLException;

public class ImageRequestHandler extends RequestHandler {
    private String method;
    private String route;
    public File file;

    public ImageRequestHandler(String imageRequest) {
        super(imageRequest);
        this.method = method();
        try {
            this.route = route();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.file = new File(route);
    }

    public String getContentType() {
        String contentType = "";
        FileHandler imageHandler = new FileHandler(file);
        for (String extension : SharedUtilities.imageExtensions) {
            if (extension.equals(imageHandler.fileExtension())) {
                contentType = extension;
            }
        }
        return contentType;
    }
}
