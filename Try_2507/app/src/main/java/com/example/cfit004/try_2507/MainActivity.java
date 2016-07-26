package com.example.cfit004.try_2507;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private SimpleAdapter adpt;
    List<Events> result = new ArrayList<Events>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adpt  = new SimpleAdapter(new ArrayList<Events>(), this);
        final ListView lView = (ListView) findViewById(R.id.listview);


        (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/events");

//        (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/completed");
        
        lView.setAdapter(adpt);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Events newsData = (Events) lView.getItemAtPosition(pos);
                 Intent i = new Intent(getApplicationContext(),NextActivity.class);
                i.putExtra("key", newsData);
                startActivity(i);


            }
        });

    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Events>> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPostExecute(List<Events> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            adpt.setItemList(result);
            adpt.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading data From Events Server...");
            dialog.show();
        }
        private Events disp(String a1,String a2)  {
            String name = a2;
            String id = a1;

            return new Events(name,id);
        }
        @Override
        protected List<Events> doInBackground(String... params) {


            try {

                URL u = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");

                conn.connect();
                InputStream is = conn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while ( is.read(b) != -1)
                    baos.write(b);

                String JSONResp = new String(baos.toByteArray());

                JSONArray arr = new JSONArray(JSONResp);
                result.add(disp("Event ID", "Event Name"));
                for (int i=0; i < arr.length(); i++) {
                    result.add(convertEventName(arr.getJSONObject(i)));
                }

                return result;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;
        }





        private Events convertEventName(JSONObject obj) throws JSONException {
            String name = obj.getString("name");
            String id = obj.getString("event_id");

            return new Events(name,id);
        }

    }



}