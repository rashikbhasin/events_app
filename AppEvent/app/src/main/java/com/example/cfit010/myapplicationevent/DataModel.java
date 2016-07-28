package com.example.cfit010.myapplicationevent;

public class DataModel {

    String id;
    String name;
    String info;
    String date;
    String venue;
    String city;

    public DataModel(String id, String name, String info, String date, String venue, String city)
    {
        this.id = id;
        this.name = name;
        this.info = info;
        this.date = date;
        this.venue = venue;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return  info;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getCity() {
        return city;
    }
}
