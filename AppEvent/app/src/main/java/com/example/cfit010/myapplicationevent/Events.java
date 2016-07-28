package com.example.cfit010.myapplicationevent;

/**
 * Created by cfit004 on 25/7/16.
 */

import java.io.Serializable;


public class Events implements Serializable {

    private String name;
    private String id;
    private String info;
    private String date;
    private String venue;
    private String city;


    public Events(String name,String id,String info,String date,String venue,String city) {
        super();
        this.name = name;
        this.id = id;
        this.info=info;
        this.date=date;
        this.venue=venue;
        this.city=city;

    }


    public String getName() {

        return name;
    }
    public String getInfo() {

        return info;
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


    public void setName(String name) {
        this.name = name;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public void setCity(String city) {
        this.city = city;
    }


    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}