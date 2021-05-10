package com.example;

import com.example.logic.bookmark.Bookmark;
import com.example.logic.bookmark.BookmarkHolder;
import com.example.logic.util.UrlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkHolderTest {
    private BookmarkHolder holder;

    @BeforeEach
    void setUp() {
        holder = new BookmarkHolder();
    }

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
        int expected = 3;

        //Act
        holder.addUrlAsBookmark("https://google.com");
        holder.addUrlAsBookmark("https://google.com");
        holder.addUrlAsBookmark("https://google.com");
        int result = holder.increaseRatingOfDuplicatedBookmark(holder.getBookmark());

        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void ensureRatingOfEmptyBookmarkIsZero(){
        //Arrange
        Bookmark bookmark = new Bookmark();
        int expected = 0;

        //Act
        int result = holder.increaseRatingOfDuplicatedBookmark(bookmark);

        //Assert
        assertEquals(expected, result);
    }


    /*
    * Ensure to detect how many urls are secure on the bookmarks list
    */
    @Test
    public void detectHowManySecureUrlsAreStored(){
        //Arrange
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
        Bookmark bookmark = new Bookmark();
        String url = "http://www.orf.at";
        String duplicateUrl = "http://www.orf.at";
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        bookmark.setUrl(url);
        bookmarks.add(bookmark);

        //Act
        boolean duplicate = holder.urlIsDuplicate(duplicateUrl, bookmarks);

        //Assert
        assertTrue(duplicate);
    }

    @Test
    public void ensureDuplicateUrlIsNotAdded(){
        //Arrange
        holder.addUrlAsBookmark("http://www.orf.at");

        //Act
        boolean duplicatedAdded = holder.addUrlAsBookmark("http://www.orf.at");

        //Assert
        assertFalse(duplicatedAdded);
    }

    @Test
    public void ensureUrlIsFromTheSameDomainIfExistingUrlIsRoot() {
        //Arrange
        String existingUrl = "http://www.orf.at";
        String newUrl = "http://www.orf.at/page1";

        //Act
        boolean fromSameDomain = holder.urlFromTheSameDomain(existingUrl, newUrl);

        //Assert
        assertTrue(fromSameDomain);
    }

    @Test
    public void ensureUrlIsFromTheSameDomainIfExistingUrlIsNotRoot(){
        //Arrange
        String existingUrl = "http://www.orf.at/page2";
        String newUrl = "http://www.orf.at/page1";

        //Act
        boolean fromSameDomain = holder.urlFromTheSameDomain(existingUrl, newUrl);

        //Assert
        assertTrue(fromSameDomain);
    }
}
