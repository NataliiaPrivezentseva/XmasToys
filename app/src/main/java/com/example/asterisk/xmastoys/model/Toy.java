package com.example.asterisk.xmastoys.model;

public class Toy {

    private String mToyName;

    private String mYear;

    private String mStory;

    private int mImageResourceId;

    public Toy(){

    }

    public Toy(String toyName, String year) {
        this.mToyName = toyName;
        this.mYear = year;
    }

    public Toy(String toyName, String year, String story, int imageResourceId) {
        this(toyName, year);
        this.mStory = story;
        this.mImageResourceId = imageResourceId;
    }

    public String getmToyName() {
        return mToyName;
    }

    public void setmToyName(String mToyName) {
        this.mToyName = mToyName;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmStory() {
        return mStory;
    }

    public void setmStory(String mStory) {
        this.mStory = mStory;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }
}