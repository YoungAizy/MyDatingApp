package com.softwares.techvibez.letsdate.models;

public class Status {
    private int imgId;
    private  String name, url, city, msg;

    public Status(int imgId, String name, String city, String msg) {
        this.imgId = imgId;
        this.name = name;
        this.city = city;
        this.msg = msg;
    }

    public Status(String name, String url, String city, String msg) {
        this.name = name;
        this.url = url;
        this.city = city;
        this.msg = msg;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
