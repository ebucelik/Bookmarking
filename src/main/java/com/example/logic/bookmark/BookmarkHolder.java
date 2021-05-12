package com.example.logic.bookmark;

import com.example.logic.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            bookmark.addAssociatedBookmark(bookmarks, bookmark);
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
        for (Bookmark bookmarkItem : bookmarks) {
            int oldRating = bookmarkItem.getRating();
            if (bookmarkItem.getUrl().equals(bookmark.getUrl())) {
                newRating = ++oldRating;
                bookmarkItem.setRating(newRating);
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

    public boolean urlFromTheSameDomain(String url1, String url2) {
        Pattern pattern = Pattern.compile("(https?:\\/\\/)(www\\.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}");
        Matcher matchUrl1 = pattern.matcher(url1);
        Matcher matchUrl2 = pattern.matcher(url2);

        boolean match = false;

        if (matchUrl1.find() && matchUrl2.find()) {
            String a = matchUrl1.group();
            String b = matchUrl2.group();

            match = a.equals(b);
        }
        return match;
    }

}
