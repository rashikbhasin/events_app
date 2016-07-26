package com.example.cfit010.myapplicationevent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cfit010.myapplicationevent.HttpRequest;
import com.example.cfit010.myapplicationevent.PostAsyncTask;
import com.example.cfit010.myapplicationevent.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cfit010 on 22/7/16.
 */
public class FormActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
    }

    public void openPostActivity(View view) {
        EditText city = (EditText) findViewById(R.id.city);
        JSONObject obj = new JSONObject();
        try {

            obj.put("city", city.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String method = getIntent().getStringExtra("method");
        TextView name=(TextView)findViewById(R.id.output);
        String url = new HttpRequest().url;
        PostAsyncTask get_request = new PostAsyncTask(name,obj.toString());
        get_request.execute(url+"/"+method);

    }

}