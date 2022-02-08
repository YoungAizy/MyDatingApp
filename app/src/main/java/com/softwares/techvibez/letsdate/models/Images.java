package com.softwares.techvibez.letsdate.models;

import android.net.Uri;

public class Images {
    private int img;

    private String url;

    private Uri uris;

    public Images(String url) {
        this.url = url;
    }

    public Images(Uri uris) {
        this.uris = uris;
    }

    public Images(int img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Uri getUris() {
        return uris;
    }

    public void setUris(Uri uris) {
        this.uris = uris;
    }
}
