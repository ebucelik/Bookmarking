package com.example.ui;

import com.example.logic.bookmark.Bookmark;
import com.example.logic.bookmark.BookmarkHolder;

public class Main {
    public static void main(String[] args) {
        BookmarkHolder bookmarkHolder = new BookmarkHolder();
        Bookmark bookmark = new Bookmark();

        bookmarkHolder.addUrlAsBookmark("http://www.orf.at");
        bookmarkHolder.addUrlAsBookmark("http://www.orf.at");


    }
}
