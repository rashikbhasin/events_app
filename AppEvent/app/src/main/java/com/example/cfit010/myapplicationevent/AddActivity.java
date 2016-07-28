package com.example.cfit010.myapplicationevent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Handler;

public class AddActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    EditText mEditText;
    private ImageButton mIb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIb = (ImageButton) findViewById(R.id.calenderButton);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        Button addButton=(Button)findViewById(R.id.submit);

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        findViewById(R.id.add_event_id).setVisibility(View.GONE);

        EditText event_id=(EditText)findViewById(R.id.add_event_id);
        event_id.setText(randomUUIDString);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        Log.d("Heree","At top");
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        mEditText = (EditText) findViewById(R.id.add_event_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // mEditText.setText(sdf.format(c.getTime()));


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


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json=new JSONObject();
                openDateActivity(view);

                try
                {
                    EditText id=(EditText)findViewById(R.id.add_event_id);
                    EditText name=(EditText)findViewById(R.id.add_event_name);
                    EditText info=(EditText)findViewById(R.id.add_event_info);
                    EditText date=(EditText)findViewById(R.id.add_event_date);
                    EditText venue=(EditText)findViewById(R.id.add_event_venue);
                    EditText city=(EditText)findViewById(R.id.add_event_city);

                    if(id.getText().toString().length()==0 || name.getText().toString().length()==0 || info.getText().toString().length()==0 || date.getText().toString().length()==0 || venue.getText().toString().length()==0 || city.getText().toString().length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Not all fields filled.Sorry data not added!!",Toast.LENGTH_LONG).show();
                        return;
                    }

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
                String serverURL=(new HttpRequest().url)+"/add";

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
                PostJson postJson=new PostJson(json,show_name,show_info,show_date,show_venue,show_city,name_head,info_head,date_head,venue_head,city_head);
                postJson.execute(serverURL);
                Toast.makeText(getApplicationContext(), "This event has been added.Redirecting To Home Screen",Toast.LENGTH_LONG).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // this code will be executed after 2 seconds
                        Intent intent=new Intent(AddActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }, 5000);
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
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            mEditText.setText(new StringBuilder().append(mYear).append("/").append(mMonth+1).append("/").append(mDay));

        }

    };

    public void openDateActivity(View view) {
        JSONObject obj = new JSONObject();
        String month = "";
        String day = "";
        if(mMonth<9)
        {
            month = "0"+String.valueOf(mMonth+1);
        }
        else
        {
            month = String.valueOf(mMonth+1);
        }
        if(mDay<10)
        {
            day = "0"+String.valueOf(mDay);
        }
        else
        {
            day = String.valueOf(mDay);
        }
        String dateFormat = String.valueOf(mYear)+"-"+month+"-"+day;
        EditText date=(EditText)findViewById(R.id.add_event_date);
        date.setText(dateFormat);

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

