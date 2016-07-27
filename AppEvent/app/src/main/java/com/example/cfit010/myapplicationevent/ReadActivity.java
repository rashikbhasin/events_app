package com.example.cfit010.myapplicationevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadActivity extends AppCompatActivity {

    Events mReadEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent=getIntent();
       mReadEvent=(Events)intent.getSerializableExtra("key");

//        View view=(View)findViewById(R.id.read_layout);
//        view.setElevation(5);

        JSONObject json=new JSONObject();

        getIntent().setAction("Already Created!");

        try
        {
            json.put("event_id",mReadEvent.getId());

        }
        catch(JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
            return ;
        }
        String serverURL=(new HttpRequest().url)+"/read";

        TextView show_name=(TextView)findViewById(R.id.read_name);
        TextView show_date=(TextView)findViewById(R.id.read_date);
        TextView show_info=(TextView)findViewById(R.id.read_info);
        TextView show_venue=(TextView)findViewById(R.id.read_venue);
        TextView show_city=(TextView)findViewById(R.id.read_city);

        PostJson postJson=new PostJson(json,show_name,show_info,show_date,show_venue,show_city);
        postJson.execute(serverURL);
    }


    @Override
    public void onResume()
    {

        String action=getIntent().getAction();
        if(action==null || !action.equals("Already Created!"))
        {
            Intent intent = new Intent(this, ReadActivity.class);
            intent.putExtra("key",mReadEvent);
            startActivity(intent);
            finish();
        }
        else
        {
            getIntent().setAction(null);

        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void update()
    {
        Intent intent=new Intent(ReadActivity.this,UpdateActivity.class);
        JSONObject data=new JSONObject();
        try{
            data.put("event_id",mReadEvent.getId());
            data.put("name",mReadEvent.getName());
            data.put("event_info",mReadEvent.getInfo());
            data.put("venue",mReadEvent.getVenue());
            data.put("date",mReadEvent.getDate());
            data.put("city",mReadEvent.getCity());
        }
        catch(JSONException e)
        {
            Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
            return ;

        }

        String sending=data.toString();
        intent.putExtra("data",sending);
        startActivity(intent);
    }

    public void delete()
    {
        Toast.makeText(getApplicationContext(), "This event has been deleted!!",Toast.LENGTH_LONG).show();
        JSONObject json=new JSONObject();

        try {
            json.put("event_id",mReadEvent.getId());

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
            return ;

        }
        String serverURL = (new HttpRequest().url)+"/deleted";
        PostJson postJson = new PostJson(json, null,null,null,null,null);
        postJson.execute(serverURL);
        Intent intent=new Intent(ReadActivity.this,MainActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.action_delete:
                delete();
                return true;
            case R.id.action_update:
                update();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

