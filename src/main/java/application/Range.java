package application;

import com.rnelson.server.utilities.SharedUtilities;

import java.util.Objects;

public class Range {
    private final String requestedRange;

    public Range(String requestedRange) {
        this.requestedRange = requestedRange;
    }

    public byte[] applyRange(byte[] content) {
        byte[] contentInRange;
        int minRange = getMinRange(content.length);
        int maxRange = getMaxRange(content.length);
        byte[] partialContent = new byte[(maxRange - minRange) + 1];
        System.arraycopy(content, minRange, partialContent, 0, partialContent.length);
        contentInRange = partialContent;
        return contentInRange;
    }

    public static Boolean excludesMin(String byteRange) {
        String match = SharedUtilities.findMatch("^[-]\\d*", byteRange, 0);
        return !Objects.equals(match, "");
    }

    public static Boolean excludesMax(String byteRange) {
        String match = SharedUtilities.findMatch("\\d*-$", byteRange, 0);
        return !Objects.equals(match, "");
    }

    public int[] minAndMaxInRange(String range, Integer contentLength) {
        int maxIndex = contentLength - 1;
        if (excludesMin(range)) {
            return finalMinMax(range, maxIndex);
        } else if (excludesMax(range)) {
            return minMaxToEnd(range, maxIndex);
        } else {
            return givenMinAndMax(range);
        }
    }

    private int[] finalMinMax(String range, int maxIndex) {
        int difference = Integer.parseInt(SharedUtilities.findMatch("\\d+", range, 0)) - 1;
        int min = maxIndex - difference;
        return toIntArray(min, maxIndex);
    }

    private int[] minMaxToEnd(String range, int maxIndex) {
        int min = Integer.parseInt(SharedUtilities.findMatch("\\d*", range, 0));
        return toIntArray(min, maxIndex);
    }

    private int[] givenMinAndMax(String range) {
        String[] splitRange = range.split("-");
        int min = Integer.parseInt(splitRange[0]);
        int max = Integer.parseInt(splitRange[1]);
        return toIntArray(min, max);
    }

    private int[] toIntArray(int min, int max) {
        return new int[]{min,max};
    }

    private int getMinRange(Integer contentLength) {
        String range = SharedUtilities.findMatch("(bytes=)(.*)", requestedRange, 2);
        int[] minAndMax = minAndMaxInRange(range, contentLength);
        return minAndMax[0];
    }

    private int getMaxRange(Integer contentLength) {
        String range = SharedUtilities.findMatch("(bytes=)(.*)", requestedRange, 2);
        int[] minAndMax = minAndMaxInRange(range, contentLength);
        return minAndMax[1];
    }
}
