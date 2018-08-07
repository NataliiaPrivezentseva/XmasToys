package com.example.asterisk.xmastoys.model;

public class Toy {

    private String mToyName;

    private String mYear;

    private String mStory;

    private int mImageResourceId;

    private String mPath;

    private String mDocumentId;

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

    public void setmToyName(String toyName) {
        this.mToyName = toyName;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String year) {
        this.mYear = year;
    }

    public String getmStory() {
        return mStory;
    }

    public void setmStory(String story) {
        this.mStory = story;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmImageResourceId(int imageResourceId) {
        this.mImageResourceId = imageResourceId;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String path) {
        this.mPath = path;
    }

    public String getmDocumentId() {
        return mDocumentId;
    }

    public void setmDocumentId(String DocumentId) {
        this.mDocumentId = DocumentId;
    }
}