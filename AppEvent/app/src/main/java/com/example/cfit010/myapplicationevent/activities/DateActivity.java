package com.example.cfit010.myapplicationevent.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.example.cfit010.myapplicationevent.HttpRequest;
import com.example.cfit010.myapplicationevent.PostAsyncTask;
import com.example.cfit010.myapplicationevent.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_activity);

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";
        mEditText = (EditText) findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mEditText.setText(sdf.format(c.getTime()));

        mEditText.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);

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
            mEditText.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));

        }

    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

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
        String date = String.valueOf(mYear)+"-"+month+"-"+day;
        try {

            obj.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String method = getIntent().getStringExtra("method");
        TextView name=(TextView)findViewById(R.id.output);
        String url =  new HttpRequest().url;
        PostAsyncTask get_request = new PostAsyncTask(name,obj.toString());
        get_request.execute(url+"/"+method);
    }
}
