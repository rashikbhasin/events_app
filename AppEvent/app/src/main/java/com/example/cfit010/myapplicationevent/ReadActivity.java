package com.example.cfit010.myapplicationevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent=getIntent();
       mReadEvent=(Events)intent.getSerializableExtra("key");

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
        String serverURL=(new MainActivity().serverUrl())+"read";
        TextView answer=(TextView)findViewById(R.id.read_content);
        PostJson postJson=new PostJson(json,answer);
        postJson.execute(serverURL);


//        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(ReadActivity.this,AddActivity.class);
//                startActivity(intent);
//
//            }
//        });

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
//        finish();
//        //Intent i=new Intent(ReadActivity.this,ReadActivity.class);
//        startActivity(getIntent());
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
//            e.printStackTrace();
        }

        String sending=data.toString();
        intent.putExtra("data",sending);
        startActivity(intent);
    }

    public void delete()
    {
//        Intent intent=new Intent(ReadActivity.this,DeleteActivity.class);
//        startActivity(intent);
        JSONObject json=new JSONObject();

        try {
//            EditText ed = (EditText)findViewById(R.id.deleteId);
//            json.put("event_id", ed.getText().toString());
            json.put("event_id",mReadEvent.getId());

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
            return ;
//            e.printStackTrace();
        }
        String serverURL = (new MainActivity().serverUrl())+"deleted";
        TextView answer = (TextView)findViewById(R.id.deletedItem);
        PostJson postJson = new PostJson(json, answer);
        postJson.execute(serverURL);
        Intent intent=new Intent(ReadActivity.this,MainActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:
                // search action
                delete();
                return true;
            case R.id.action_update:
                // location found
                update();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);


            //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
        }
    }
}

