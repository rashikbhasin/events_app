package com.example.cfit010.myapplicationevent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {


    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button addButton=(Button)findViewById(R.id.submit);

        Log.d("Heree","At top");
        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";
        mEditText = (EditText) findViewById(R.id.add_event_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mEditText.setText(sdf.format(c.getTime()));

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


//                    json.put("event_id","3");
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
                String serverURL=(new MainActivity().serverUrl())+"add";
                TextView answer=(TextView)findViewById(R.id.new_added);
                PostJson postJson=new PostJson(json,answer);
                postJson.execute(serverURL);
            }
        });
    }
    //                    json.put("date",date.getText().toString());
//    Log.d("Heree",mDate);


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
//            mEditText.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));
            mEditText.setText(new StringBuilder().append(mYear).append("/").append(mMonth+1).append("/").append(mDay));

        }

    };

    public void openDateActivity(View view) {
        JSONObject obj = new JSONObject();
        String month = "";
        String day = "";
        if(mMonth<10)
        {
            month = "0"+String.valueOf(mMonth+1);
        }
        else
        {
            month = String.valueOf(mMonth);
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

//          mDate = String.valueOf(mYear)+"-"+month+"-"+day;
//        try {
//
//            obj.put("date", k-date);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String method = getIntent().getStringExtra("method");
//        TextView name=(TextView)findViewById(R.id.output);
//        String url =  new HttpRequest().url;
//        PostAsyncTask get_request = new PostAsyncTask(name,obj.toString());
//        get_request.execute(url+"/"+method);
    }



}

