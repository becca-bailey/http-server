package com.rnelson.server.request;

import com.rnelson.server.utilities.SharedUtilities;

import java.util.Base64;

public class Credentials {
    private final String encodedCredentials;

    Credentials(String encodedCredentials) {
        this.encodedCredentials = encodedCredentials;
    }

    private byte[] decodeToBytes() {
        String encoding = SharedUtilities.findMatch("\\S+$", encodedCredentials, 0);
        return Base64.getDecoder().decode(encoding);
    }

    private String[] splitDecodedString() {
        byte[] decodedBytes = decodeToBytes();
        String decodedString = new String(decodedBytes);
        return decodedString.split(":");
    }

    private String decodedUsername() {
        return splitDecodedString()[0];
    }

    private String decodedPassword() {
        return splitDecodedString()[1];
    }

    public Boolean isUsername(String usernameToCheck) {
        return usernameToCheck.equals(decodedUsername());
    }

    public Boolean isPassword(String passwordToCheck) {
        return passwordToCheck.equals(decodedPassword());
    }
}
