package com.rnelson.server.serverTests;

import com.rnelson.server.request.RequestData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RequestDataTest {
    private final RequestData data = new RequestData();

    @Test
    public void setBodyAddsBodyDataToMap() throws Throwable {
        data.setBody("this is some body text");
        Map<String,String> response = data.returnAllData();
        assertEquals("this is some body text", response.get("body"));
    }

    @Test
    public void addDataMergesTwoMaps() throws Throwable {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("my", "parameters");
        data.addData(parameters);
        Map<String,String> response = data.returnAllData();
        assertEquals("parameters", response.get("my"));
        assertEquals(1, response.size());
    }
}
