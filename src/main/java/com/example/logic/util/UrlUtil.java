package com.example.logic.util;

import java.util.regex.Pattern;

public class UrlUtil {


    /**
     * Validate an URL
     *
     * @param urlToCheck url to check
     * @return true if URL is valid, false if URL is invalid
     */
    public static boolean validateUrl(String urlToCheck) {
        String URL_REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return Pattern.matches(URL_REGEX, urlToCheck);
    }
}
