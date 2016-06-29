package com.rnelson.server.request;

import com.rnelson.server.content.FileHandler;
import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;

public class ImageRequest extends RequestHandler {
    private String method;
    private String route;
    public File file;

    public ImageRequest(String imageRequest) {
        super(imageRequest);
        this.route = route();
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
