package com.example.asterisk.xmastoys.model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Toy implements Serializable{

    @NonNull
    private String mToyName = "Toy";

    private String mYear;

    private String mStory;

    private int mImageResourceId;

    private String mPath;

    @Exclude
    private String mDocumentId;

    public Toy(){

    }

    public Toy(@NonNull String toyName, String year) {
        this.mToyName = toyName;
        this.mYear = year;
    }

    public Toy(String toyName, String year, String story, int imageResourceId) {
        this(toyName, year);
        this.mStory = story;
        this.mImageResourceId = imageResourceId;
    }

    @NonNull
    public String getmToyName() {
        return mToyName;
    }

    public void setmToyName(@NonNull String toyName) {
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