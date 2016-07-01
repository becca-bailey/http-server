package com.rnelson.server.serverTests;

import com.rnelson.server.request.Parameters;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ParametersTest {
    private final Parameters parameters = new Parameters("variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");

    @Test
    public void splitParametersReturnsParametersArray() throws Throwable {
        String[] splitParameters = new String[2];
        splitParameters[0] = "variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        splitParameters[1] = "variable_2=stuff";
        assertEquals(splitParameters[0], parameters.splitParameters()[0]);
        assertEquals(splitParameters[1], parameters.splitParameters()[1]);
    }

    @Test
    public void numberOfParametersReturnsNumberOfParameters() throws Throwable {
        Integer n = 2;
        assertEquals(n, parameters.numberOfParameters());
    }

    @Test
    public void getKeyReturnsParameterKeyForNthParameter() throws Throwable {
        assertEquals("variable_1", parameters.getKey(1));
        assertEquals("variable_2", parameters.getKey(2));
    }

    @Test
    public void getValueReturnsParameterValueForNthParameter() throws Throwable {
        assertEquals("Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F", parameters.getValue(1));
        assertEquals("stuff", parameters.getValue(2));
    }

    @Test
    public void decodedValueReturnsDecodedStringForNthParameter() throws Throwable {
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", parameters.decodedValue(1));
        assertEquals("stuff", parameters.decodedValue(2));
    }

    @Test
    public void getDecodedParametersReturnsKeyValuePairs() throws Throwable {
        Map<String, String> parameterKeysAndValues = new HashMap<>();
        parameterKeysAndValues.put("variable_1", "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?");
        parameterKeysAndValues.put("variable_2", "stuff");
        assertEquals(parameterKeysAndValues, parameters.getDecodedParameters());
    }
}
