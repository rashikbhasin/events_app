package com.example.cfit010.myapplicationevent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cfit010.myapplicationevent.HttpRequest;
import com.example.cfit010.myapplicationevent.PostAsyncTask;
import com.example.cfit010.myapplicationevent.R;

import org.json.JSONException;
import org.json.JSONObject;


public class FormActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
//            case R.id.action_back:
//                finish();
//                return true;
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}