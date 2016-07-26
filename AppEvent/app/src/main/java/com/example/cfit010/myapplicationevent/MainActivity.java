package com.example.cfit010.myapplicationevent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

    static final String METHOD = "method";
    private SimpleAdapter adpt;
    List<Events> result = new ArrayList<Events>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        adpt  = new SimpleAdapter(new ArrayList<Events>(), this);
        final ListView lView = (ListView) findViewById(R.id.listview);

        String method = getIntent().getStringExtra("method");

        if(method!=null) {
            if (method.equals("upcoming"))
                (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/" + method);

            else if (method.equals("completed"))
                (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/" + method);
        }
        else
            (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/events");

//        (new AsyncListViewLoader()).execute("http://192.168.3.3:8080/completed");

        lView.setAdapter(adpt);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Events newsData = (Events) lView.getItemAtPosition(pos);
                /*Intent i = new Intent(getApplicationContext(), NextActivity.class);
                i.putExtra("key", newsData);
                startActivity(i);*/


            }
        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcomming_events) {
            // Handle the camera action
            Intent intent = new Intent(this,MainActivity.class);//this,GetActivity.class
            intent.putExtra(METHOD,"upcoming");
            startActivity(intent);

        } else if (id == R.id.nav_completed_events) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(METHOD,"completed");
            startActivity(intent);

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
