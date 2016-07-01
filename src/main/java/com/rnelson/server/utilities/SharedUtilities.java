package com.rnelson.server.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SharedUtilities {

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
        List<String> matches = new ArrayList<>();
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

    public static String capitalize(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
