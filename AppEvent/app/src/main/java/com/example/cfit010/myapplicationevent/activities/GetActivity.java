package com.example.cfit010.myapplicationevent.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cfit010.myapplicationevent.GetAsyncTask;
import com.example.cfit010.myapplicationevent.HttpRequest;
import com.example.cfit010.myapplicationevent.R;

public class GetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_frame);
        TextView name=(TextView)findViewById(R.id.text1);
        String method = getIntent().getStringExtra("method");
        String url = new HttpRequest().url;
        GetAsyncTask get_request = new GetAsyncTask(name);
        get_request.execute(url+"/"+method);
    }


}
