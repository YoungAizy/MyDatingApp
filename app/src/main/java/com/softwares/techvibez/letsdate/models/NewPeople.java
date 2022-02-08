package com.softwares.techvibez.letsdate.models;

public class NewPeople{

    private String name, url, Id;
    private int imgId;

    public NewPeople(String name, String url, String id, int imgId) {
        this.name = name;
        this.url = url;
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

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
