package com.rnelson.server.routing;

import com.rnelson.server.utilities.SharedUtilities;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Route {
    final String url;
    public String controllerPrefix;
    Boolean isProtected = false;
    private String rootName = "Root";
    private final Set<String> methods = new HashSet<>();

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

    public void protect() {
        this.isProtected = true;
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

    public Set<String> getMethods() {
        return methods;
    }

    private Boolean isRootPath(String url) {
        return url.equals("/");
    }

    public File getFile(String directoryName) {
        return new File(directoryName + url);
    }
}
