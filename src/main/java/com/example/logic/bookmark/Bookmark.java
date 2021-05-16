package com.example.logic.bookmark;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Bookmark {
    private String url;

    private List<String> keyword;
    private List<Bookmark> bookmarksOfSameDomain;
    private Timestamp timestamp;

    private int rating;

    public Bookmark() {
        // Validate URL here and if invalid - throw exception

        keyword = new ArrayList<String>();
        bookmarksOfSameDomain = new ArrayList<Bookmark>();
        timestamp = null;
        rating = 0;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    /*
     * Add multiple keywords to a bookmark
     * @param bookmark The bookmark object where to add the keywords
     * @returns true if the keyword is added successfully otherwise false if the object is null
     * */
    public boolean addKeyword(String keyword) {
        if (url == null || keyword == null || url.trim().equals("") || keyword.trim().equals("")) return false;

//        if (this.keyword.stream().anyMatch(element -> element.equals(keyword))) return false;
        boolean isOkay = true;
        for (String keywords : this.keyword) {
            if (keywords.equals(keyword))
                isOkay = false;
        }

        if (isOkay)
            this.keyword.add(keyword);

        return isOkay;
    }

    public List<Bookmark> addAssociatedBookmark(List<Bookmark> bookmarks, Bookmark newBookmark) {
        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        List<Bookmark> bookmarkList = new ArrayList<Bookmark>();
        for (Bookmark b : bookmarks) {
            boolean sameDomain = bookmarkHolder.urlFromTheSameDomain(b.url, newBookmark.url);
            if (sameDomain && !b.getUrl().equals(newBookmark.getUrl())) {
                bookmarkList.add(b);
                newBookmark.bookmarksOfSameDomain.add(b);
                b.bookmarksOfSameDomain.add(newBookmark);
            }
        }
        return bookmarkList;
    }

    public List<Bookmark> getBookmarksOfSameDomain(Bookmark bookmark) {
        return new ArrayList<Bookmark>(bookmark.bookmarksOfSameDomain);
    }

    public List<String> removeKeyword(String keyword) {
        if (this.keyword.contains(keyword)) {
            this.keyword.remove(keyword);
        } else {
            throw new NoSuchElementException();
        }
        return this.keyword;
    }

    public Timestamp createTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
