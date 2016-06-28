package com.rnelson.server.controllers;

import com.rnelson.server.Controller;
import com.rnelson.server.file.DirectoryHandler;
import com.rnelson.server.response.Response;
import com.rnelson.server.utilities.SharedUtilities;

public class RootController extends Controller {
    public byte[] get() {
        byte[] header = (Response.status(200) + "\r\n\r\n").getBytes();
        DirectoryHandler directory = new DirectoryHandler("public/");
        // ^ change to directory
        byte[] body =  directory.getDirectoryLinks().getBytes();
        return SharedUtilities.addByteArrays(header, body);
    }
}
