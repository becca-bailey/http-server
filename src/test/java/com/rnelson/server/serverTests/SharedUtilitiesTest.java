package com.rnelson.server.serverTests;

import com.rnelson.server.utilities.SharedUtilities;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SharedUtilitiesTest {
    @Test
    public void addByteArraysAddsByteArrays() throws Throwable {
        byte[] hello = ("Hello").getBytes();
        byte[] world = ("World").getBytes();
        byte[] helloWorld = SharedUtilities.addByteArrays(hello, world);
        String testString = new String(helloWorld, "UTF-8");
        assertEquals("HelloWorld", testString);
    }

    @Test
    public void addByteArraysConcatenatesEmptyArrays() throws Throwable {
        byte[] emptyArray = new byte[0];
        String emptyString = new String(SharedUtilities.addByteArrays(emptyArray, emptyArray), "UTF-8");
        assertEquals("", emptyString);
    }
}
