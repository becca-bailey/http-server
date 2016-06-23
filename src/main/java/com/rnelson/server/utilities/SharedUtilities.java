package com.rnelson.server.utilities;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.*;

public class SharedUtilities {
    public static List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "svg", "gif", "bmp");

    public static String findMatch(String regex, String request, int group) {
        String match = null;
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            match = matcher.group(group).trim();
        }
        return match;
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
}
