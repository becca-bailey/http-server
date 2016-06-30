package com.rnelson.server.utilities;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.*;

public class SharedUtilities {
    public static List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "svg", "gif", "bmp");

    public static String findMatch(String regex, String line, int group) {
        String match = "";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            match = matcher.group(group).trim();
        }
        return match;
    }

    public static List<String> findAllMatches(String regex, String line) {
        List<String> matches = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    public static byte[] addByteArrays(byte[] array1, byte[] array2) {
        byte[] concatenatedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, concatenatedArray, 0, array1.length);
        System.arraycopy(array2, 0, concatenatedArray, array1.length, array2.length);
        return concatenatedArray;
    }

    public static String convertByteArrayToString(byte[] bytes) {
        String convertedString = "";
        try {
            convertedString = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return convertedString;
    }

    public static String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
