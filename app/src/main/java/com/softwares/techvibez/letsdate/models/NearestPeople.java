package com.softwares.techvibez.letsdate.models;

public class NearestPeople {

    private String name, distance, url, Id;
    private int imgId;

    public NearestPeople(String name, String distance, String id, int imgId) {
        this.name = name;
        this.distance = distance;
        Id = id;
        this.imgId = imgId;
    }

    public NearestPeople(String name, String distance, String url, String id) {
        this.name = name;
        this.distance = distance;
        this.url = url;
        Id = id;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
