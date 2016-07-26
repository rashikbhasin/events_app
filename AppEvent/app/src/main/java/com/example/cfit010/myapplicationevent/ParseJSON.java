package com.example.cfit010.myapplicationevent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ParseJSON {


    public String parse(String json)
    {
        String data = "";
        try {

        JSONArray jsonArray = new JSONArray(json);

        //Iterate the jsonArray and print the info of JSONObjects
        for(int i=0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String id = jsonObject.getString("event_id");
            String info = jsonObject.getString("event_info");
            String date = jsonObject.getString("date");
            String venue = jsonObject.getString("venue");
            String city = jsonObject.getString("city");

            data += " EVENT NAME"+"  : "+name+" \n EVENT ID          : "+ id +" \n EVENT INFO     : "+ info +" \n EVENT DATE    : "+ date +" \n EVENT VENUE : "+venue+" \n EVENT CITY      : "+city+" \n";
            data += " \n";
        }
            return data;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }



}
