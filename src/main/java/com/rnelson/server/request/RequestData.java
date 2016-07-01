package com.rnelson.server.request;

import java.util.HashMap;
import java.util.Map;

public class RequestData {
    private final Map<String, String> data = new HashMap<>();

    public void setBody(String requestBody) {
        data.put("body", requestBody);
    }

    public Map<String,String> returnAllData() {
        return data;
    }

    public void addData(Map<String, String> mapData) {
        data.putAll(mapData);
    }
}
