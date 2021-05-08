package com.example.logic.bookmark;

import java.util.ArrayList;
import java.util.List;

public class Bookmark {
    private String url;

    private List<String> keyword;

    public Bookmark(){
        keyword = new ArrayList<String>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    /*
     * Add multiple keywords to a bookmark
     * @param bookmark The bookmark object where to add the keywords
     * @returns true if the keyword is added successfully otherwise false if the object is null
     * */
    public boolean addKeyword(String keyword){
        if(url == null || keyword == null) return false;
        else if(url.trim().equals("") || keyword.trim().equals("")) return false;

        if(this.keyword.stream().anyMatch(element -> element.equals(keyword))) return false;

        this.keyword.add(keyword);

        return true;
    }
}
