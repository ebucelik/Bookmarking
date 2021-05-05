package com.example;

import com.example.logic.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookmarkTest {

    /**
     * Ensure that a valid URL will be validated correctly
     * @param input URL to validate
     * @param expected Validation Result of the Util Class
     */
    @ParameterizedTest
    @CsvSource({"http://www.google.at/,true", "https://www.orf.at/,true", "http://orf.at/,true"})
    public void ensureValidUrlWillBeValidated(String input, boolean expected) {
        // Arrange
        UrlUtil urlUtil = new UrlUtil();

        // Act
        boolean result = urlUtil.validateUrl(input);

        //Assert
        assertEquals(expected, result);
    }

    // Test Case - what if there is no URL in the List?
    // Test Case - what if there is already URL in List?
    // Test Case - what if the URL is valid?
    // Test Case - what if the URL is not valid?


}
