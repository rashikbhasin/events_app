package com.example.cfit010.myapplicationevent;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class PostJson extends AsyncTask<String ,Void,String> {

    private JSONObject mJson;
    private TextView name;
    private TextView info;
    private TextView date;
    private TextView venue;
    private TextView city;
    private TextView name_head;
    private TextView info_head;
    private TextView date_head;
    private TextView venue_head;
    private TextView city_head;


    public PostJson(JSONObject json, TextView name, TextView info,TextView date,TextView venue,TextView city,TextView nhead,TextView dhead,TextView ihead,TextView vhead,TextView chead) {
        this.mJson = json;
        this.name=name;
        this.info=info;
        this.date=date;
        this.venue=venue;
        this.city=city;
        this.city_head=chead;
        this.name_head=nhead;
        this.date_head=dhead;
        this.info_head=ihead;
        this.venue_head=vhead;
    }

    protected String doInBackground(String... urls) {
        String output = "";
        try {


            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();

            os.write(mJson.toString().getBytes());
            os.flush();


            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String x = "";
            while ((x = br.readLine()) != null) {
                output = output + x;
            }

            conn.disconnect();

        } catch (IOException e) {
            return "";

        }
        return output;
    }

    protected void onPostExecute(String output) {
        String data = "";
        try {
            JSONObject j = new JSONObject(output);
            if (j.length() == 1) {
                return;
            } else {
//                data = data + "Name:  " + j.getString("name") + "\n" + "Event Info:  " + j.getString("event_info") + "\n" + "Date:  " + j.getString("date") + "\n" + "Venue:  " + j.getString("venue") + "\n" + "City:  " + j.getString("city");
                name_head.setText("Name:");
                info_head.setText("Info:");
                date_head.setText("Date:");
                venue_head.setText("Venue:");
                city_head.setText("City:");
                name.setText(j.getString("name"));
                info.setText(j.getString("event_info"));
                date.setText(j.getString("date"));
                venue.setText(j.getString("venue"));
                city.setText(j.getString("city"));

            }
        } catch (JSONException e) {
            return ;
        }

    }
}