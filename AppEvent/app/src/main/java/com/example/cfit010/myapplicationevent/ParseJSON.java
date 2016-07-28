package com.example.cfit010.myapplicationevent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ParseJSON {


    public List<DataModel> parse(String json)
    {
        ArrayList<DataModel> list_result = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(json);
            DataModel model;

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String id = jsonObject.getString("event_id");
                String info = jsonObject.getString("event_info");
                String date = jsonObject.getString("date");
                String venue = jsonObject.getString("venue");
                String city = jsonObject.getString("city");

                model = new DataModel(id, name, info, date, venue, city);
                list_result.add(model);
            }
            return list_result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list_result;
    }



}
