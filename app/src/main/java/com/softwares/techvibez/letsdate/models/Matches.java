package com.softwares.techvibez.letsdate.models;

public class Matches {
    private String name, url, Id;
    private int imgId;

    public Matches(String name, String url, String id) {
        this.name = name;
        this.url = url;
        Id = id;
    }

    public Matches(String name, String id, int imgId) {
        this.name = name;
        Id = id;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getImgId() {
        return imgId;
    }
}
