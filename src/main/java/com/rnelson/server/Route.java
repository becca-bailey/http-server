package com.rnelson.server;

import com.rnelson.server.utilities.SharedUtilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Route {
    public String url;
    String rootName = "Root";
    Set<String> methods = new HashSet<String>();

    public Route(String url) {
        this.url = url;
    }

    public Route(String url, String defaultRootName) {
        this.url = url;
        this.rootName = defaultRootName;
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
}
