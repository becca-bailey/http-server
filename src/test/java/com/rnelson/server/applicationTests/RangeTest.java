package com.rnelson.server.applicationTests;

import application.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RangeTest {

    @Test
    public void excludesMinReturnsTrueOrFalse() throws Throwable {
        assertTrue(Range.excludesMin("-6"));
        assertFalse(Range.excludesMin("40-"));
    }

    @Test
    public void excludesMaxReturnsTrueOrFalse() throws Throwable {
        assertTrue(Range.excludesMax("40-"));
        assertFalse(Range.excludesMax("-6"));
    }

    @Test
    public void minAndMaxRangeReturnsRangeIf2ValuesAreGiven() throws Throwable {
        int[] minAndMax = new int[] {0, 4};
        Range range = new Range("bytes=0-4");

        assertEquals(minAndMax[0], range.minAndMaxInRange("0-4", 76)[0]);
        assertEquals(minAndMax[1], range.minAndMaxInRange("0-4", 76)[1]);
    }

    @Test
    public void minAndMaxRangeReturnsFinalRange() throws Throwable {
        int[] minAndMax = new int[] {70, 75};
        Range range = new Range("bytes=-6");

        assertEquals(minAndMax[0], range.minAndMaxInRange("-6", 76)[0]);
        assertEquals(minAndMax[1], range.minAndMaxInRange("-6", 76)[1]);
    }

    @Test
    public void minAndMaxRangeReturnsRangeToEnd() throws Throwable {
        int[] minAndMax = new int[] {4, 75};
        Range range = new Range("bytes=4-");
        assertEquals(minAndMax[0], range.minAndMaxInRange("4-", 76)[0]);
        assertEquals(minAndMax[1], range.minAndMaxInRange("4-", 76)[1]);
    }
}
