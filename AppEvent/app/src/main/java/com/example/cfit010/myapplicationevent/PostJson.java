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

//    TextView answer=(TextView)findViewById(R.id.output);

    private JSONObject mJson;
    private TextView mPrintHere;

    public PostJson(JSONObject json, TextView output) {
        this.mJson = json;
        this.mPrintHere = output;
    }

    protected String doInBackground(String... urls) {
        String output = "";
        try {


            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //String input = "{\"event_id\":\"3\",\"name\":\"aamya\",\"event_info\":\"info 3\",\"date\":\"2019-07-02\",\"venue\":\"venue 3\",\"city\":\"mumbai\"}";

            OutputStream os = conn.getOutputStream();


            //os.write(input.getBytes());
            os.write(mJson.toString().getBytes());
            os.flush();


            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String x = "";
            //System.out.println("Output from Server .... \n");
            while ((x = br.readLine()) != null) {
                //System.out.println(output);
                output = output + x;
            }

            conn.disconnect();

        } catch (IOException e) {
            return "";
//            e.printStackTrace();

        }
        return output;
    }

    protected void onPostExecute(String output) {
        String data = "";
        try {
            JSONObject j = new JSONObject(output);
            if (j.length() == 1) {
//                data = data + "Deleted Event With id:" + j.getString("Deleted");
//                mPrintHere.setText(data);
            } else {
                data = data + "Name:  " + j.getString("name") + "\n" + "Event Info:  " + j.getString("event_info") + "\n" + "Date:  " + j.getString("date") + "\n" + "Venue:  " + j.getString("venue") + "\n" + "City:  " + j.getString("city");
                mPrintHere.setText(data);

            }
        } catch (JSONException e) {
            return ;
//            e.printStackTrace();
        }

    }
}