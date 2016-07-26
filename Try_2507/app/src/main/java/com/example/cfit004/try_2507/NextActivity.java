package com.example.cfit004.try_2507;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by cfit004 on 25/7/16.
 */
public class NextActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_next);
        Intent i = getIntent();
        Events data=(Events) i.getSerializableExtra("key");
        final TextView tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setText("Hi this ");


    }

}
