package com.example.logic.bookmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkTest {
    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        bookmark = new Bookmark();
    }


    /*
     * Ensure that single keywords can be added to one bookmark
     * @param keyword: Check if single keyword can be added
     * */
    @ParameterizedTest
    @CsvSource({"Fun, true"})
    public void ensureSingleKeywordWillBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    /*
     * Ensure that multiple keywords can be added to one bookmark
     * @param keyword: Check if multiple keywords can be added
     * */
    @ParameterizedTest
    @CsvSource({"Fun, true", "Science, true", "Digital, true"})
    public void ensureMultipleKeywordsWillBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    /*
     * Ensure that empty strings can't be added to a bookmark
     * @param keyword: Check if empty keyword's can't be added to the list
     * */
    @ParameterizedTest
    @CsvSource({"Fun, true", ", false", "'', false"})
    public void ensureEmptyKeywordCantBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    /*
     * Ensure that duplicated keywords can't be add to a bookmark
     * @param keyword: Check if duplicated keyword won't be added to the list
     * */
    @ParameterizedTest
    @CsvSource({"Science, false"})
    public void ensureDuplicatedKeywordCantBeAddedToBookmark(String keyword, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");

        bookmark.addKeyword("Science");
        boolean result = bookmark.addKeyword(keyword);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void ensureBookmarkFromSameDomainIsAddedToExistingBookmark(){
        //Arrange
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        Bookmark existingBookmark = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        String existingUrl = "http://www.google.at/page1";
        existingBookmark.setUrl(existingUrl);
        newBookmark.setUrl("http://www.google.at");
        bookmarks.add(existingBookmark);

        //Act
        List<Bookmark> addAssociatedBookmark = bookmark.addAssociatedBookmark(bookmarks, newBookmark);

        //Assert
        assertEquals(existingUrl, addAssociatedBookmark.get(0).getUrl());
    }

    @Test
    public void ensureBookmarkFromOtherDomainIsNotAddedToBookmark(){
        //Arrange
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        Bookmark existingBookmark = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark.setUrl("http://www.google.at/page1");
        newBookmark.setUrl("http://www.google.com/page1");
        bookmarks.add(existingBookmark);

        //Act
        List<Bookmark> addAssociatedBookmark = bookmark.addAssociatedBookmark(bookmarks, newBookmark);

        //Assert
        assertTrue(addAssociatedBookmark.isEmpty());
    }

    @Test
    public void ensureExistingBookmarkFromSameDomainIsAddedToNewBookmark(){
        //Arrange
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        Bookmark existingBookmark = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark.setUrl("http://www.google.at/page1");
        newBookmark.setUrl("http://www.google.at");
        bookmarks.add(existingBookmark);

        //Act
        bookmark.addAssociatedBookmark(bookmarks, newBookmark);
        List<Bookmark> a = existingBookmark.getBookmarksOfSameDomain(newBookmark);
        boolean isInside = a.contains(existingBookmark);

        //Assert
        assertTrue(isInside);
    }

    @Test
    public void ensureNewBookmarkIsAssociatedToAllExistingBookmarksOfSameDomain(){
        //Arrange
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        Bookmark existingBookmark1 = new Bookmark();
        Bookmark existingBookmark2 = new Bookmark();
        Bookmark existingBookmark3 = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark1.setUrl("http://www.google.at/page1");
        existingBookmark2.setUrl("http://www.google.at/page2");
        existingBookmark3.setUrl("http://www.google.at/page3");
        newBookmark.setUrl("http://www.google.at");
        bookmarks.add(existingBookmark1);
        bookmarks.add(existingBookmark2);
        bookmarks.add(existingBookmark3);

        //Act
        bookmark.addAssociatedBookmark(bookmarks, newBookmark);
        List<Bookmark> associatedBookmarksOfBookmark1 = bookmark.getBookmarksOfSameDomain(existingBookmark1);
        List<Bookmark> associatedBookmarksOfBookmark2 = bookmark.getBookmarksOfSameDomain(existingBookmark1);
        List<Bookmark> associatedBookmarksOfBookmark3 = bookmark.getBookmarksOfSameDomain(existingBookmark1);
        boolean isInsideBookmark1 = associatedBookmarksOfBookmark1.contains(newBookmark);
        boolean isInsideBookmark2 = associatedBookmarksOfBookmark2.contains(newBookmark);
        boolean isInsideBookmark3 = associatedBookmarksOfBookmark3.contains(newBookmark);

        //Assert
        assertTrue(isInsideBookmark1);
        assertTrue(isInsideBookmark2);
        assertTrue(isInsideBookmark3);
    }

    @Test
    public void ensureExistingBookmarksOfSameDomainAreAddedToNewBookmark(){
        //Arrange
        List<Bookmark> bookmarks = new ArrayList<Bookmark>();
        Bookmark existingBookmark1 = new Bookmark();
        Bookmark existingBookmark2 = new Bookmark();
        Bookmark existingBookmark3 = new Bookmark();
        Bookmark newBookmark = new Bookmark();
        existingBookmark1.setUrl("http://www.google.at/page1");
        existingBookmark2.setUrl("http://www.google.at/page2");
        existingBookmark3.setUrl("http://www.google.at/page3");
        newBookmark.setUrl("http://www.google.at");
        bookmarks.add(existingBookmark1);
        bookmarks.add(existingBookmark2);
        bookmarks.add(existingBookmark3);

        //Act
        bookmark.addAssociatedBookmark(bookmarks, newBookmark);
        List<Bookmark> associatedBookmarksOfNewBookmark = bookmark.getBookmarksOfSameDomain(newBookmark);
        boolean isInsideNewBookmark = associatedBookmarksOfNewBookmark.containsAll(bookmarks);

        //Assert
        assertTrue(isInsideNewBookmark);
    }

}
