package com.rnelson.server.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Parameters {
    private final String originalParameters;
    private final String[] splitParameters;

    public Parameters(String parameters) {
        this.originalParameters = parameters;
        this.splitParameters = splitParameters();
    }

    public String[] splitParameters() {
        return originalParameters.split("&");
    }

    public Integer numberOfParameters() {
        return splitParameters.length;
    }

    private String[] keyValuePair(int i) {
        return splitParameters[i].split("=");
    }

    public String getKey(int nth) {
        int i = nth - 1;
        return keyValuePair(i)[0];
    }

    public String getValue(int nth) {
        int i = nth - 1;
        return keyValuePair(i)[1];
    }

    public String decodedValue(int nth) {
        String originalValue = getValue(nth);
        String decoded = "";
        try {
            decoded = URLDecoder.decode(originalValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decoded;
    }

    public Map<String, String> getDecodedParameters() {
        Map<String, String> decodedParameters = new HashMap<>();
        Parameters parameters = new Parameters(originalParameters);
        for (int i = 0; i < parameters.numberOfParameters(); i++) {
            decodedParameters.put(parameters.getKey(i + 1), parameters.decodedValue(i + 1));
        }
        return decodedParameters;
    }
}
