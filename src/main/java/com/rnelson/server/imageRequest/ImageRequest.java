package com.rnelson.server.imageRequest;

import com.rnelson.server.fileHandler.FileHandler;
import com.rnelson.server.request.RequestHandler;
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
