package com.softwares.techvibez.letsdate.models;

public class UsersDB {
    private String Name;
    private String imageUrl;

    public UsersDB() {
    }

    public UsersDB(String name, String path) {
        Name = name;
        imageUrl = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
