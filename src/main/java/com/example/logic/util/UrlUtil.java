package com.example.logic.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {

    /**
     * Validate an URL
     *
     * @param urlToCheck url to check
     * @return true if URL is valid, false if URL is invalid
     */
    public boolean validateUrl(String urlToCheck) {
        try {
            new URL(urlToCheck);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
