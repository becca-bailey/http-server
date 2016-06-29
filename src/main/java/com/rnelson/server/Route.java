package com.rnelson.server;

import com.rnelson.server.utilities.SharedUtilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Route {
    String url;
    public String controllerPrefix;
    private String rootName = "Root";
    Set<String> methods = new HashSet<String>();

    public Route(String url) {
        this.url = url;
    }

    public Route(String url, String controllerPrefix) {
        this.url = url;
        this.controllerPrefix = controllerPrefix;
        if (isRootPath(url)) {
            this.rootName = controllerPrefix;
        }
    }

    public void addMethod(String method) {
        methods.add(method);
    }

    public Boolean hasMethod(String method) {
        return methods.contains(method);
    }

    public int countMethods() {
        return methods.size();
    }

    public List<String> getPaths() {
        return SharedUtilities.findAllMatches("\\w+", url);
    }

    public String getClassName() {
        List<String> paths = getPaths();
        if (paths.size() > 0) {
            return SharedUtilities.capitalize(paths.get(0));
        } else {
            return rootName;
        }
    }

    private Boolean isRootPath(String url) {
        return url.equals("/");
    }
}
