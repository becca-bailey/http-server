package application;

import com.rnelson.server.utilities.SharedUtilities;

public class Range {
    String requestedRange;

    public Range(String requestedRange) {
        this.requestedRange = requestedRange;
    }

    public byte[] applyRange(byte[] content) {
        byte[] contentInRange = content;
        int minRange = getMinRange(content.length);
        int maxRange = getMaxRange(content.length);
        byte[] partialContent = new byte[(maxRange - minRange) + 1];
        System.arraycopy(content, minRange, partialContent, 0, partialContent.length);
        contentInRange = partialContent;
        return contentInRange;
    }

    public static Boolean excludesMin(String byteRange) {
        String match = SharedUtilities.findMatch("^[-]\\d*", byteRange, 0);
        return match != "";
    }

    public static Boolean excludesMax(String byteRange) {
        String match = SharedUtilities.findMatch("\\d*-$", byteRange, 0);
        return match != "";
    }

    public int[] minAndMaxInRange(String range, Integer contentLength) {
        int highestIndex = contentLength - 1;
        if (excludesMin(range)) {
            return finalMinMax(range, highestIndex);
        } else if (excludesMax(range)) {
            return minMaxToEnd(range, highestIndex);
        } else {
            return givenMinAndMax(range);
        }
    }

    private int[] finalMinMax(String range, int highestIndex) {
        int difference = Integer.parseInt(SharedUtilities.findMatch("\\d+", range, 0)) - 1;
        int min = highestIndex - difference;
        int max = highestIndex;
        return toIntArray(min, max);
    }

    private int[] minMaxToEnd(String range, int highestIndex) {
        int min = Integer.parseInt(SharedUtilities.findMatch("\\d*", range, 0));
        int max = highestIndex;
        return toIntArray(min, max);
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
