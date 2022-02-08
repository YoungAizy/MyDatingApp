package com.softwares.techvibez.letsdate.models;

public class TestModel {
    private String name, city, url;
    private String userId;
    private static String ownerId;
    //private int image;


    public TestModel(String name, String city, String url, String userId) {
        this.name = name;
        this.city = city;
        this.url = url;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String uId) {
        userId = uId;
    }

    public static String getOwnerId() {
        return ownerId;
    }

    public static void setOwnerId(String ownerId) {
        TestModel.ownerId = ownerId;
    }
}
