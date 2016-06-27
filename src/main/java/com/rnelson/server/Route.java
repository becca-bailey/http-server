package com.rnelson.server;

import java.util.HashSet;
import java.util.Set;

public class Route {
    String url;
    Set<String> methods = new HashSet<String>();

    public Route(String url) {
        this.url = url;
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
}
