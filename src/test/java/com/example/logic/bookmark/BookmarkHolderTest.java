package com.example.logic.bookmark;

import com.example.logic.util.UrlUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkHolderTest {
/*
    private BookmarkHolder holder;

    // Can collidate with other Tests
    @BeforeEach
    void setUp() {
        holder = new BookmarkHolder();
    }
*/

    /**
     * Ensure that a valid URL will be validated correctly
     *
     * @param input    URL to validate
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
     *
     * @param input    URL to validate
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
     *
     * @param inputUrl Valid URL to add to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http://orf.at/", "http://www.orf.at/", "https://www.google.at/"})
    public void ensureValidUrlWillBeAddedToBookmark(String inputUrl) {
        // Act
        BookmarkHolder holdernew = new BookmarkHolder();
        boolean result = holdernew.addUrlAsBookmark(inputUrl);

        // Assert
        assertTrue(result);
    }

    /**
     * Ensure that invalid URL will not be added to the bookmark
     *
     * @param inputUrl Invalid URL trying to be added to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http:/orf.at/", "htps://www.google.at", "https://www.google."})
    public void ensureInvalidUrlWillNotBeAddedToBookmark(String inputUrl) {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        // Act
        boolean result = holder.addUrlAsBookmark(inputUrl);

        // Assert
        assertFalse(result);
    }

    /**
     * Ensure that valid URL will be added to the bookmark which already has URLs in the List
     *
     * @param inputUrl Valid URL to add to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http://orf.at/", "https://www.google.at", "https://google.com"})
    public void ensureValidUrlWillBeAddedToAlreadyUsedBookmark(String inputUrl) {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        // Act
        holder.addUrlAsBookmark("https://orf.at/");
        boolean result = holder.addUrlAsBookmark(inputUrl);

        // Assert
        assertTrue(result);
    }

    /**
     * Ensure that invalid URL will not be added to the bookmark which already has URLs in the List
     *
     * @param inputUrl Invalid URL to add to the bookmark
     */
    @ParameterizedTest
    @CsvSource({"http:/orf.at/", "htps://www.google.at", "https://www.google."})
    public void ensureInvalidUrlWillNotBeAddedToAlreadyUsedBookmark(String inputUrl) {
        //Arrange
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
    public void ensureRatingIncreasesAtDuplicatedBookmarks() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
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
    public void ensureRatingOfEmptyBookmarkIsZero() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        Bookmark bookmark = new Bookmark();
        int expected = 0;

        //Act
        int result = holder.increaseRatingOfDuplicatedBookmark(bookmark);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    public void ensureRatingStaysZeroWithAlreadyAddedBookmarks() {
        // Arrange
        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        bookmarkHolder.addUrlAsBookmark("https://google.at");
        Bookmark bookmark = new Bookmark();
        bookmark.setUrl("https://orf.at");

        // Act
        int result = bookmarkHolder.increaseRatingOfDuplicatedBookmark(bookmark);

        // Assert
        assertEquals(0, result);
    }


    /*
     * Ensure to detect how many urls are secure on the bookmarks list
     */
    @Test
    public void detectHowManySecureUrlsAreStored() {
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
    public void ensureDuplicateUrlIsNotAdded() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        holder.addUrlAsBookmark("http://www.orf.at");

        //Act
        boolean duplicatedAdded = holder.addUrlAsBookmark("http://www.orf.at");

        //Assert
        assertFalse(duplicatedAdded);
    }

    @Test
    public void ensureUrlIsFromTheSameDomainIfExistingUrlIsRoot() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        String existingUrl = "http://www.orf.at";
        String newUrl = "http://www.orf.at/page1";

        //Act
        boolean fromSameDomain = holder.urlFromTheSameDomain(existingUrl, newUrl);

        //Assert
        assertTrue(fromSameDomain);
    }

    @Test
    public void ensureUrlIsFromTheSameDomainIfExistingUrlIsNotRoot() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        String existingUrl = "http://www.orf.at/page2";
        String newUrl = "http://www.orf.at/page1";

        //Act
        boolean fromSameDomain = holder.urlFromTheSameDomain(existingUrl, newUrl);

        //Assert
        assertTrue(fromSameDomain);
    }

    @Test
    public void ensureUrlIsNotFromTheSameDomain() {
        // Arrange
        String existingUrl = "https://google.at";
        String newUrl = "http://www.orf.at/page1";
        BookmarkHolder bookmarkHolder = new BookmarkHolder();

        // Act
        boolean notFromSameDomain = bookmarkHolder.urlFromTheSameDomain(existingUrl, newUrl);

        // Assert
        assertFalse(notFromSameDomain);
    }

    @Test
    public void ensureWhenAddingANewBookmarkItIsAssociated() {
        //Arrange
        BookmarkHolder holder = new BookmarkHolder();
        Bookmark bookmark = new Bookmark();
        String url = "http://www.orf.at";
        holder.addUrlAsBookmark(url);
        String newUrl = "http://www.orf.at/page2";
        holder.addUrlAsBookmark(newUrl);

        //Act
        Bookmark b = holder.getBookmark();
        List<Bookmark> bookmarks = bookmark.getBookmarksOfSameDomain(b);
        boolean containsBookmark = bookmarks.stream().anyMatch(bookm -> bookm.getUrl().equals(url));

        //Assert
        assertTrue(containsBookmark);
    }

    //User Story Six: Filter bookmarks by one keyword
    /*
     * Ensure that user can get a list of bookmarks by filtering through one keyword
     * */
    @Test
    public void ensureToFilterBookmarksByOneKeyword() {
        //Arrange
        List<String> filteredKeywords = Collections.singletonList("Science");

        List<Bookmark> expectedBookmarks = new ArrayList<>();

        BookmarkHolder bookmarkHolderExpected = new BookmarkHolder();
        bookmarkHolderExpected.addUrlAsBookmark("https://www.fh-campuswien.ac.at");
        expectedBookmarks.add(bookmarkHolderExpected.getBookmark());

        bookmarkHolderExpected.addUrlAsBookmark("https://www.tu-wien.at");
        expectedBookmarks.add(bookmarkHolderExpected.getBookmark());

        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        bookmarkHolder.addUrlAsBookmark("https://www.google.at");
        bookmarkHolder.getBookmark().addKeyword("Search");
        bookmarkHolder.getBookmark().addKeyword("Home");

        bookmarkHolder.addUrlAsBookmark("https://www.facebook.com");
        bookmarkHolder.getBookmark().addKeyword("Social Media");

        bookmarkHolder.addUrlAsBookmark("https://www.fh-campuswien.ac.at");
        bookmarkHolder.getBookmark().addKeyword("Science");
        bookmarkHolder.getBookmark().addKeyword("Informatics");

        bookmarkHolder.addUrlAsBookmark("https://www.tu-wien.at");
        bookmarkHolder.getBookmark().addKeyword("Science");

        //Act
        List<Bookmark> resultBookmarks = bookmarkHolder.getBookmarksByKeyword(filteredKeywords);

        List<String> expectedUrl = new ArrayList<>();
        for (Bookmark bookmarkItem : expectedBookmarks) {
            expectedUrl.add(bookmarkItem.getUrl());
        }

        List<String> resultUrl = new ArrayList<>();
        for (Bookmark bookmarkItem : resultBookmarks) {
            resultUrl.add(bookmarkItem.getUrl());
        }

        //Assert
        assertEquals(expectedUrl, resultUrl);
    }

    @Test
    public void ensureToNotFindBookmarksByOneKeyword() {
        //Arrange
        List<String> filteredKeywords = Collections.singletonList("Science");

        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        bookmarkHolder.addUrlAsBookmark("https://www.google.at");
        bookmarkHolder.getBookmark().addKeyword("Search");
        bookmarkHolder.getBookmark().addKeyword("Home");

        bookmarkHolder.addUrlAsBookmark("https://www.facebook.com");
        bookmarkHolder.getBookmark().addKeyword("Social Media");

        bookmarkHolder.addUrlAsBookmark("https://www.fh-campuswien.ac.at");
        bookmarkHolder.getBookmark().addKeyword("Informatics");

        bookmarkHolder.addUrlAsBookmark("https://www.tu-wien.at");
        bookmarkHolder.getBookmark().addKeyword("Sport");

        //Act
        List<Bookmark> resultBookmarks = bookmarkHolder.getBookmarksByKeyword(filteredKeywords);

        List<String> resultUrl = new ArrayList<>();
        for (Bookmark bookmarkItem : resultBookmarks) {
            resultUrl.add(bookmarkItem.getUrl());
        }

        //Assert
        assertEquals(new ArrayList<>(), resultUrl);
    }

    //User Story Seven: Filter bookmarks by one or more keywords
    /*
     * Ensure that user can get a list of bookmarks by filtering through one or more keywords
     * */
    @Test
    public void ensureToFilterBookmarksByMultipleKeywords() {
        //Arrange
        List<String> filteredKeywords = Arrays.asList("Science", "Informatics", "Fun", "Facebook");

        List<Bookmark> expectedBookmarks = new ArrayList<>();

        BookmarkHolder bookmarkHolderExpected = new BookmarkHolder();
        bookmarkHolderExpected.addUrlAsBookmark("https://www.facebook.com");
        expectedBookmarks.add(bookmarkHolderExpected.getBookmark());

        bookmarkHolderExpected.addUrlAsBookmark("https://www.fh-campuswien.ac.at");
        expectedBookmarks.add(bookmarkHolderExpected.getBookmark());

        bookmarkHolderExpected.addUrlAsBookmark("https://www.tu-wien.at");
        expectedBookmarks.add(bookmarkHolderExpected.getBookmark());

        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        bookmarkHolder.addUrlAsBookmark("https://www.google.at");
        bookmarkHolder.getBookmark().addKeyword("Search");
        bookmarkHolder.getBookmark().addKeyword("Home");

        bookmarkHolder.addUrlAsBookmark("https://www.facebook.com");
        bookmarkHolder.getBookmark().addKeyword("Social Media");
        bookmarkHolder.getBookmark().addKeyword("Facebook");

        bookmarkHolder.addUrlAsBookmark("https://www.fh-campuswien.ac.at");
        bookmarkHolder.getBookmark().addKeyword("Science");
        bookmarkHolder.getBookmark().addKeyword("Informatics");

        bookmarkHolder.addUrlAsBookmark("https://www.tu-wien.at");
        bookmarkHolder.getBookmark().addKeyword("Science");

        //Act
        List<Bookmark> resultBookmarks = bookmarkHolder.getBookmarksByKeyword(filteredKeywords);

        List<String> expectedUrl = new ArrayList<>();
        for (Bookmark bookmarkItem : expectedBookmarks) {
            expectedUrl.add(bookmarkItem.getUrl());
        }

        List<String> resultUrl = new ArrayList<>();
        for (Bookmark bookmarkItem : resultBookmarks) {
            resultUrl.add(bookmarkItem.getUrl());
        }

        //Assert
        assertEquals(expectedUrl, resultUrl);
    }

    @Test
    public void ensureToNotFindBookmarksByMultipleKeywords() {
        //Arrange
        List<String> filteredKeywords = Arrays.asList("Science", "Mathematics", "Fun", "Facebook");

        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        bookmarkHolder.addUrlAsBookmark("https://www.google.at");
        bookmarkHolder.getBookmark().addKeyword("Search");
        bookmarkHolder.getBookmark().addKeyword("Home");

        bookmarkHolder.addUrlAsBookmark("https://www.facebook.com");
        bookmarkHolder.getBookmark().addKeyword("Social Media");

        bookmarkHolder.addUrlAsBookmark("https://www.fh-campuswien.ac.at");
        bookmarkHolder.getBookmark().addKeyword("Informatics");
        bookmarkHolder.getBookmark().addKeyword("Physics");

        bookmarkHolder.addUrlAsBookmark("https://www.tu-wien.at");
        bookmarkHolder.getBookmark().addKeyword("Sport");

        //Act
        List<Bookmark> resultBookmarks = bookmarkHolder.getBookmarksByKeyword(filteredKeywords);

        List<String> resultUrl = new ArrayList<>();
        for (Bookmark bookmarkItem : resultBookmarks) {
            resultUrl.add(bookmarkItem.getUrl());
        }

        //Assert
        assertEquals(new ArrayList<>(), resultUrl);
    }

    @Test
    public void ensureRemovingBookmarkIsRemovedFromList() {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();
        holder.addUrlAsBookmark("https://www.google.at");
        holder.addUrlAsBookmark("https://www.tu-wien.at");

        //Act
        Bookmark bookmark = holder.getBookmark();
        List<Bookmark> bookmarks = holder.removeBookmark(bookmark);
        boolean isRemoved = !bookmarks.contains(bookmark);
        boolean listNotEmpty = !bookmarks.isEmpty();

        //Assert
        assertTrue(isRemoved);
        assertTrue(listNotEmpty);
    }

    @Test
    public void ensureRemovingBookmarkThatDoesNotExistThrowsException() {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();
        holder.addUrlAsBookmark("https://www.google.at");
        Bookmark bookmark = new Bookmark();
        bookmark.setUrl("https://www.tu-wien.at");

        //Act && Assert
        assertThrows(NoSuchElementException.class, () -> {
            holder.removeBookmark(bookmark);
        });
    }

    @Test
    public void ensureRemovingBookmarkReturnsEmptyList() {
        // Arrange
        BookmarkHolder holder = new BookmarkHolder();
        holder.addUrlAsBookmark("https://www.google.at");

        //Act
        Bookmark bookmark = holder.getBookmark();
        List<Bookmark> bookmarks = holder.removeBookmark(bookmark);
        boolean listEmpty = bookmarks.isEmpty();

        //Assert
        assertTrue(listEmpty);
    }
}
