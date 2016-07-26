package com.example.cfit010.myapplicationevent;

/**
 * Created by cfit004 on 25/7/16.
 */

import java.io.Serializable;


public class Events implements Serializable {

    private String name;
    private String id;


    public Events(String name,String id) {
        super();
        this.name = name;
        this.id = id;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}