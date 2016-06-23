package com.rnelson.server.unitTests;

import com.rnelson.server.response.BodyContent;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class BodyContentTest {
    BodyContent content = new BodyContent("GET", "/");

    @Test
    public void finalBytesReturnsTrueOrFalse() throws Throwable {
        assertTrue(content.finalBytes("-6"));
        assertFalse(content.finalBytes("40-"));
    }

    @Test
    public void bytesToEndReturnsTrueOrFalse() throws Throwable {
        assertTrue(content.bytesToEnd("40-"));
        assertFalse(content.bytesToEnd("-6"));
    }

    @Test
    public void minAndMaxRangeReturnsRangeIf2ValuesAreGiven() throws Throwable {
        Integer[] range = new Integer[] {0, 4};
        assertEquals(range[0], content.minAndMaxInRange("0-4", 76)[0]);
        assertEquals(range[1], content.minAndMaxInRange("0-4", 76)[1]);
    }

    @Test
    public void minAndMaxRangeReturnsFinalRange() throws Throwable {
        Integer[] range = new Integer[] {70, 75};
        assertEquals(range[0], content.minAndMaxInRange("-6", 76)[0]);
        assertEquals(range[1], content.minAndMaxInRange("-6", 76)[1]);
    }

    @Test
    public void minAndMaxRangeReturnsRangeToEnd() throws Throwable {
        Integer[] range = new Integer[] {4, 75};
        assertEquals(range[0], content.minAndMaxInRange("4-", 76)[0]);
        assertEquals(range[1], content.minAndMaxInRange("4-", 76)[1]);
    }
}
