package com.example.cfit010.myapplicationevent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cfit010.myapplicationevent.activities.DateActivity;
import com.example.cfit010.myapplicationevent.activities.FormActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    public String serverUrl()
//    {
//        return "http://192.168.3.3:8080/";
//    }

    static final String METHOD = "method";
    private SimpleAdapter adpt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

            }
        });



        adpt  = new SimpleAdapter(new ArrayList<Events>(), this);
        final ListView lView = (ListView) findViewById(R.id.listview);

       // String method = getIntent().getStringExtra(METHOD);

        //(new AsyncListViewLoader()).execute("http://192.168.3.3:8080/events");

//        (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/completed");

        lView.setAdapter(adpt);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Events newsData = (Events) lView.getItemAtPosition(pos);
//                Intent i = new Intent(MainActivity.this, ReadActivity.class);
//                i.putExtra("key", newsData);
//                startActivity(i);

                Intent intent=new Intent(MainActivity.this, ReadActivity.class);
                intent.putExtra("key",newsData);
                startActivity(intent);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        adpt.clear();
        (new AsyncListViewLoader()).execute(new HttpRequest().url+"/events");
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcomming_events) {
            adpt.clear();
            (new AsyncListViewLoader()).execute((new HttpRequest().url)+"/upcoming");

        } else if (id == R.id.nav_completed_events) {
            adpt.clear();
            (new AsyncListViewLoader()).execute((new HttpRequest().url)+"/completed");

        } else if (id == R.id.nav_city) {
            Intent intent = new Intent(this,FormActivity.class);
            intent.putExtra(METHOD,"search-city");
            startActivity(intent);

        } else if (id == R.id.nav_data) {
            Intent intent = new Intent(this,DateActivity.class);
            intent.putExtra(METHOD,"search-date");
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Events>> {
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        List<Events> result = new ArrayList<>();

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
//            dialog.setMessage("Downloading data From Events Server...");
//            dialog.show();
        }
//        private Events disp(String a1,String a2)  {
//
//            return new Events(a2, a1," "," "," "," ");
//        }
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
               // result.add(disp("Event ID", "Event Name"));
                for (int i=0; i < arr.length(); i++) {
                    result.add(convertEventName(arr.getJSONObject(i)));
                }

                return result;
            }
            catch(Throwable t) {
                t.printStackTrace();
                return null;
            }

        }

        private Events convertEventName(JSONObject obj) throws JSONException {
            String name = obj.getString("name");
            String id = obj.getString("event_id");
            String info = obj.getString("event_info");
            String date = obj.getString("date");
            String venue= obj.getString("venue");
            String city = obj.getString("city");

            return new Events(name,id,info,date,venue,city);
        }

    }
}
