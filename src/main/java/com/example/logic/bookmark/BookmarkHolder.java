package com.example.logic.bookmark;

import com.example.logic.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class BookmarkHolder {
    private List<Bookmark> bookmarks;
    private Bookmark bookmark;

    /**
     * Initialize the list with the Bookmarks - Empty
     */
    public BookmarkHolder() {
        bookmarks = new ArrayList<>();
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    /**
     * Only add valid URL to the Bookmark List
     *
     * @param url URL to add
     * @return <b>true</b> if URL got added to the list, <b>false</b> if URL could not be added to the list
     */
    public boolean addUrlAsBookmark(String url) {
        if (!UrlUtil.validateUrl(url)) {
            return false;
        }

        bookmark = new Bookmark();
        bookmark.setUrl(url);
        if (!urlIsDuplicate(url, bookmarks)) {
            bookmarks.add(bookmark);
            return true;
        } else {
            increaseRatingOfDuplicatedBookmark(bookmark);
            return false;
        }
    }

    /*
     * Increase the rating of a bookmark if its an duplication
     * @param bookmark Bookmark which's rating should be increased in order to stay unique
     * @return the new rating number
     * */
    public int increaseRatingOfDuplicatedBookmark(Bookmark bookmark) {
        int newRating = 0;
        for(Bookmark b : bookmarks) {
            int oldRating = b.getRating();
            if(b.getUrl().equals(bookmark.getUrl())) {
                newRating = ++oldRating;
                b.setRating(newRating);
                return newRating;
            }
        }
        return newRating;
    }


    /*
     * Count the secured urls from the bookmarks list
     * @return number of secured urls
     * */
    public int countSecureUrls() {
        return (int) bookmarks.stream().filter(bookmark -> bookmark.getUrl().substring(0, 5).contains("https")).count();
    }

    public boolean urlIsDuplicate(String inputUrl, List<Bookmark> bookmarks) {
        return bookmarks.stream().anyMatch(bookmark1 -> bookmark1.getUrl().equals(inputUrl));
    }

    public boolean urlFromTheSameDomain(String inputUrl, String existingUrl) {
        return inputUrl.startsWith(existingUrl);
    }
}
