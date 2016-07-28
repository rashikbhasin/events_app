package com.example.cfit010.myapplicationevent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UpdateActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    EditText mEditText;
    int flag=0;
    private ImageButton mIb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        findViewById(R.id.add_event_id).setVisibility(View.GONE);


        mIb = (ImageButton) findViewById(R.id.calenderButton);

        Intent intent=getIntent();
        final String received=intent.getStringExtra("data");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        mEditText = (EditText) findViewById(R.id.add_event_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JSONObject reader;

        mIb.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);

            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);

            }
        });

        Button button=(Button)findViewById(R.id.submit);
        button.setText("UPDATE");
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
                openDateActivity(view);

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

                TextView name_head=(TextView)findViewById(R.id.name_head);
                TextView info_head=(TextView)findViewById(R.id.info_head);
                TextView date_head=(TextView)findViewById(R.id.date_head);
                TextView venue_head=(TextView)findViewById(R.id.venue_head);
                TextView city_head=(TextView)findViewById(R.id.city_head);
//                try{
//                    if(reader.getString("name").equals(id.getText().toString()) ||reader.getString("info").equals(info.getText().toString()) || reader.getString("date").equals(name.getText().toString()) || reader.getString("venue").equals(venue.getText().toString()) || reader.getString("city").equals(city.getText().toString()))
//                    {
//                        Toast.makeText(getApplicationContext(), "You Didn't Updated Anything!!",Toast.LENGTH_LONG).show();
//                        return ;
//                    }
//
//                    else{
//                        Log.d("Hereee",name.getText().toString());
//                        Log.d("Hereee",venue.getText().toString());
//                        Log.d("Hereee",info.getText().toString());
//                        Log.d("Hereee",date.getText().toString());
//                        Log.d("Hereee",city.getText().toString());
//                        PostJson postJson=new PostJson(json,show_name,show_info,show_date,show_venue,show_city,name_head,info_head,date_head,venue_head,city_head);
//                        postJson.execute(serverURL);
//                    }
//                }
//                catch(JSONException e)
//                {
//                    Toast.makeText(getApplicationContext(), "JSON Exception",Toast.LENGTH_LONG).show();
//                    return ;
//                }

                PostJson postJson=new PostJson(json,show_name,show_info,show_date,show_venue,show_city,name_head,info_head,date_head,venue_head,city_head);
                postJson.execute(serverURL);

            }
        });
    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            flag=1;
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            mEditText.setText(new StringBuilder().append(mYear).append("/").append(mMonth+1).append("/").append(mDay));

        }

    };

    public void openDateActivity(View view) {
//        JSONObject obj = new JSONObject();
        if(flag==1) {
            String month = "";
            String day = "";
            if (mMonth < 9) {
                month = "0" + String.valueOf(mMonth + 1);
            } else {
                month = String.valueOf(mMonth+1);
            }
            if (mDay < 10) {
                day = "0" + String.valueOf(mDay);
            } else {
                day = String.valueOf(mDay);
            }
            String dateFormat = String.valueOf(mYear) + "-" + month + "-" + day;
            EditText date = (EditText) findViewById(R.id.add_event_date);
            date.setText(dateFormat);
        }
        else
        {
            return;
        }

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
