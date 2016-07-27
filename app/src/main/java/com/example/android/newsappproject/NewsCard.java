package com.example.android.newsappproject;

/**
 * Created by da7th on 7/27/2016.
 */
public class NewsCard {

    private String mType;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public NewsCard(String type, String title, String date, String url) {
        mType = type;
        mTitle = title;
        mDate = date;
        mUrl = url;
    }

    public String getType() {
        return mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
