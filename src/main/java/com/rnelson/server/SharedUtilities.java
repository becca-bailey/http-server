package com.rnelson.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SharedUtilities {

    public static String findMatch(String regex, String request, int group) {
        String match = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            match = matcher.group(group).trim();
        }
        return match;
    }
}
