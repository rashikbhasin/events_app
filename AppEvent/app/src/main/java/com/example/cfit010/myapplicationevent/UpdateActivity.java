package com.example.cfit010.myapplicationevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        findViewById(R.id.add_event_id).setVisibility(View.GONE);


        Intent intent=getIntent();
        final String received=intent.getStringExtra("data");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        JSONObject reader;

        Button button=(Button)findViewById(R.id.submit);
        final EditText id=(EditText)findViewById(R.id.add_event_id);
        final EditText name=(EditText)findViewById(R.id.add_event_name);
        final EditText info=(EditText)findViewById(R.id.add_event_info);
        final EditText date=(EditText)findViewById(R.id.add_event_date);
        final EditText venue=(EditText)findViewById(R.id.add_event_venue);
        final EditText city=(EditText)findViewById(R.id.add_event_city);

        try{
            reader=new JSONObject(received);
            id.setText(reader.getString("event_id"));
            name.setText(reader.getString("name"));
            info.setText(reader.getString("event_info"));
            date.setText(reader.getString("date"));
            venue.setText(reader.getString("venue"));
            city.setText(reader.getString("city"));

        }catch(JSONException e)
        {
            Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
            return ;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json=new JSONObject();
                try
                {
                    json.put("event_id",id.getText().toString());
                    json.put("name",name.getText().toString());
                    json.put("event_info",info.getText().toString());
                    json.put("date",date.getText().toString());
                    json.put("venue",venue.getText().toString());
                    json.put("city",city.getText().toString());

                }
                catch(JSONException e) {
                    Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
                    return ;
                }
                String serverURL=(new HttpRequest().url)+"/update";
                TextView show_name=(TextView)findViewById(R.id.read_name);
                TextView show_date=(TextView)findViewById(R.id.read_date);
                TextView show_info=(TextView)findViewById(R.id.read_info);
                TextView show_venue=(TextView)findViewById(R.id.read_venue);
                TextView show_city=(TextView)findViewById(R.id.read_city);
                PostJson postJson=new PostJson(json,show_name,show_info,show_date,show_venue,show_city);
                postJson.execute(serverURL);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
