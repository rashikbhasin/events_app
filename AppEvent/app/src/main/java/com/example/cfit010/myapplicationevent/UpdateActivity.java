package com.example.cfit010.myapplicationevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        Intent intent=getIntent();
        final String received=intent.getStringExtra("data");

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
//            e.printStackTrace();
        }

//                    json.put("event_id","3");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json=new JSONObject();


                try
                {

//                    json.put("name","aamya");
//                    json.put("event_info","info 3");
//                    json.put("date","2011-02-03");
//                    json.put("venue","venue 3");
//                    json.put("city","mumbai");

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
//                    e.printStackTrace();
                }
                String serverURL=(new MainActivity().serverUrl())+"update";
                TextView answer=(TextView)findViewById(R.id.new_added);
                PostJson postJson=new PostJson(json,answer);
                postJson.execute(serverURL);

            }
        });
    }


}
