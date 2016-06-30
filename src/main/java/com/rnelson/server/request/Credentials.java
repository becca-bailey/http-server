package com.rnelson.server.request;

import com.rnelson.server.utilities.SharedUtilities;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

public class Credentials {
    String encodedCredentials;

    public Credentials(String encodedCredentials) {
        this.encodedCredentials = encodedCredentials;
    }

    private byte[] decodeToBytes() {
        String encoding = SharedUtilities.findMatch("\\S+$", encodedCredentials, 0);
        return decodeBase64(encoding);
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
