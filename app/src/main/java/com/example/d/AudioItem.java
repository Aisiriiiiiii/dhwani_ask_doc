package com.example.d;

public class AudioItem {
     String atitle;
    String aurl;

    public AudioItem(String title, String url) {
        this.atitle = title;
        this.aurl = url;
    }

    public String getTitle() {

        return atitle;
    }

    public String getUrl() {
        return aurl;
    }
}
