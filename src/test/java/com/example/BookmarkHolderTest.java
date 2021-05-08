package com.example;

import com.example.logic.bookmark.BookmarkHolder;
import com.example.logic.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkHolderTest {

    /**
     * Ensure that a valid URL will be validated correctly
     * @param input URL to validate
     * @param expected Validation Result of the Util Class
     */
    @ParameterizedTest
    @CsvSource({"http://www.google.at/,true", "https://www.orf.at/,true", "http://orf.at/,true"})
    public void ensureValidUrlWillBeValidated(String input, boolean expected) {
        // Arrange// Act
        boolean result = UrlUtil.validateUrl(input);

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Ensure that invalid URL will be invalidated correctly
     * @param input URL to validate
     * @param expected Validation Result of the Util Class
     */
    @ParameterizedTest
    @CsvSource({"http:/orf.at/,false"})
    public void ensureInvalidUrlWillBeInvalidated(String input, boolean expected) {
        // Arrange
        // Act
        boolean result = UrlUtil.validateUrl(input);

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Ensure that valid URL will be added to the bookmark
     * @param inputUrl Valid URL to add to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http://orf.at/", "http://www.orf.at/", "https://www.google.at/"})
    public void ensureValidUrlWillBeAddedToBookmark(String inputUrl) {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();

        // Act
        boolean result = holder.addUrlAsBookmark(inputUrl);

        // Assert
        assertTrue(result);
    }

    /**
     * Ensure that invalid URL will not be added to the bookmark
     * @param inputUrl Invalid URL trying to be added to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http:/orf.at/", "htps://www.google.at", "https://www.google."})
    public void ensureInvalidUrlWillNotBeAddedToBookmark(String inputUrl) {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();

        // Act
        boolean result = holder.addUrlAsBookmark(inputUrl);

        // Assert
        assertFalse(result);
    }

    /**
     * Ensure that valid URL will be added to the bookmark which already has URLs in the List
     * @param inputUrl Valid URL to add to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http://orf.at/", "https://www.google.at", "https://google.com"})
    public void ensureValidUrlWillBeAddedToAlreadyUsedBookmark(String inputUrl) {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();

        // Act
        holder.addUrlAsBookmark("https://orf.at/");
        boolean result = holder.addUrlAsBookmark(inputUrl);

        // Assert
        assertTrue(result);
    }

    /**
     * Ensure that invalid URL will not be added to the bookmark which already has URLs in the List
     * @param inputUrl Invalid URL to add to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http:/orf.at/", "htps://www.google.at", "https://www.google."})
    public void ensureInvalidUrlWillNotBeAddedToAlreadyUsedBookmark(String inputUrl) {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();

        // Act
        holder.addUrlAsBookmark("https://orf.at/");
        boolean result = holder.addUrlAsBookmark(inputUrl);

        // Assert
        assertFalse(result);
    }

    /*
    * Ensure that rating of an duplicated bookmark increases
    */
    @Test
    public void ensureRatingIncreasesAtDuplicatedBookmarks(){
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        int expected = 3;

        //Act
        holder.addUrlAsBookmark("https://google.at");
        holder.addUrlAsBookmark("https://google.com");
        holder.addUrlAsBookmark("https://google.com");
        holder.addUrlAsBookmark("https://google.com");
        int result = holder.increaseRatingOfDuplicatedBookmark(holder.getBookmark());

        //Assert
        assertEquals(expected, result);
    }


    /*
    * Ensure to detect how many urls are secure on the bookmarks list
    */
    @Test
    public void detectHowManySecureUrlsAreStored(){
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        int expected = 2;

        //Act
        holder.addUrlAsBookmark("https://www.google.at");
        holder.addUrlAsBookmark("http://www.facebook.com");
        holder.addUrlAsBookmark("https://www.twitter.com");
        holder.addUrlAsBookmark("http://www.instagram.com");
        int result = holder.countSecureUrls();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void ensureDuplicateUrlsAreFound() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        String inputUrl = "http://www.orf.at";
        String existingUrl = "http://www.orf.at";

        //Act
        boolean duplicate = holder.urlIsDuplicate(inputUrl, existingUrl);

        //Assert
        assertTrue(duplicate);
    }

    @Test
    public void ensureUrlIsFromTheSameDomain() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        String inputUrl = "http://www.orf.at/home";
        String existingUrl = "http://www.orf.at";

        //Act
        boolean fromSameDomain = holder.urlFromTheSameDomain(inputUrl, existingUrl);

        //Assert
        assertTrue(fromSameDomain);
    }


}
