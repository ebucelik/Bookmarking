package com.example.logic.bookmark;

import com.example.logic.util.UrlUtil;

import java.util.ArrayList;
import java.util.List;

public class BookmarkHolder {
    private List<String> bookmarks;

    /**
     * Initialize the list with the Bookmarks - Empty
     */
    public BookmarkHolder() {
        bookmarks = new ArrayList<>();
    }

    /**
     * Only add valid URL to the Bookmark List
     * @param url URL to add
     * @return <b>true</b> if URL got added to the list, <b>false</b> if URL could not be added to the list
     */
    public boolean addUrlAsBookmark(String url) {
        if (!UrlUtil.validateUrl(url)) {
            return false;
        }

        bookmarks.add(url);
        return true;
    }
}
