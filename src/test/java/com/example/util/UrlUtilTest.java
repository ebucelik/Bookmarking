package com.example.util;

import com.example.logic.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlUtilTest {

    @ParameterizedTest
    @CsvSource({"http://www.orf.at/", "https://www.google.at", "http://www.google.com"})
    public void ensureEnteredUrlMatchesUrlRegex(String url){
        boolean matches = UrlUtil.validateUrl(url);
        assertTrue(matches);
    }

    @ParameterizedTest
    @CsvSource({"htt://www.orf.at/", "https://www.google.", "http:/www.google.com"})
    public void ensureEnteredUrlDoesNotMatchesUrlRegex(String url){
        boolean matches = UrlUtil.validateUrl(url);
        assertFalse(matches);
    }

    @Test
    public void ensureCreatingUrlUtilWillNotCrash() {
        UrlUtil urlUtil = new UrlUtil();
    }
}
