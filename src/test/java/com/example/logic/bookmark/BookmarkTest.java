package com.example.logic.bookmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BookmarkTest {
    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        bookmark = new Bookmark();
    }

    //Ensure that single keywords can be added to one bookmark
    @ParameterizedTest
    @CsvSource({"Fun, true"})
    public void ensureSingleKeywordWillBeAddedToBookmark(String input, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(input);

        //Assert
        assertEquals(expected, result);
    }

    // Ensure that multiple keywords can be added to one bookmark
    @ParameterizedTest
    @CsvSource({"Fun, true", "Science, true", "Digital, true"})
    public void ensureMultipleKeywordWillBeAddedToBookmark(String input, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(input);

        //Assert
        assertEquals(expected, result);
    }

    // Ensure that empty strings can't be added to a bookmark
    @ParameterizedTest
    @CsvSource({"Fun, true", ", false"})
    public void ensureEmptyKeywordCantBeAddedToBookmark(String input, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");
        boolean result = bookmark.addKeyword(input);

        //Assert
        assertEquals(expected, result);
    }

    // Ensure that duplicated keywords can't be add to a bookmark
    @ParameterizedTest
    @CsvSource({"Science, false"})
    public void ensureDuplicatedKeywordCantBeAddedToBookmark(String input, boolean expected){
        //Act
        bookmark.setUrl("http://orf.at/");

        bookmark.addKeyword("Science");
        boolean result = bookmark.addKeyword(input);

        //Assert
        assertEquals(expected, result);
    }
}
