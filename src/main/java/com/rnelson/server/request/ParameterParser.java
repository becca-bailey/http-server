package com.rnelson.server.request;

import com.rnelson.server.utilities.SharedUtilities;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParameterParser {
    String originalString;
    String[] splitParameters;

    public ParameterParser(String parameters) {
        this.originalString = parameters;
        this.splitParameters = splitParameters();
    }

    public String[] splitParameters() {
        return originalString.split("&");
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
}
