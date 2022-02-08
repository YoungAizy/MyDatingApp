package com.softwares.techvibez.letsdate.models;

public class Spot {
    Long id;
    String url, name , city;

    private static long counter;

    public Spot(Long id, String url, String name, String city) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.city = city;
    }


    private static long getCounter() {
        return Spot.counter;
    }

    private static void setCounter(long var1) {
        Spot.counter = var1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
