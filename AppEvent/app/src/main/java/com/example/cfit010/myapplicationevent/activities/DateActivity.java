package com.example.cfit010.myapplicationevent.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cfit010.myapplicationevent.HttpRequest;
import com.example.cfit010.myapplicationevent.PostAsyncTask;
import com.example.cfit010.myapplicationevent.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateActivity extends AppCompatActivity {

    private int mYear,mMonth,mDay;
    EditText mEditText;
    private ImageButton mIb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_activity);

        mIb = (ImageButton) findViewById(R.id.calenderButton);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       // mIb = (ImageButton) findViewById(R.id.calenderButton);

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH)+1;
        mDay=c.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";
        mEditText = (EditText) findViewById(R.id.date);


        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mEditText.setText(sdf.format(c.getTime()));*/
        mEditText.setText(new StringBuilder().append(mYear).append("/").append(mMonth).append("/").append(mDay));


        findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        findViewById(R.id.calenderButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });



    }


    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear+1;
            mDay = dayOfMonth;
            mEditText.setText(new StringBuilder().append(mYear).append("/").append(mMonth).append("/").append(mDay));
               /* Toast.makeText(
                        DateActivity.this,
                        String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                                + "-" + String.valueOf(dayOfMonth),
                        Toast.LENGTH_LONG).show();*/
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void openDateActivitySearch(View view) {
        JSONObject json_obj = new JSONObject();
        String month;
        String day;
        if(mMonth<9)
        {
            month = "0"+String.valueOf(mMonth);
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
        String date = String.valueOf(mYear)+"-"+month+"-"+day;
        try {

            json_obj.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String method = getIntent().getStringExtra("method");
        TextView name=(TextView)findViewById(R.id.output);
        ListView list_view=(ListView)findViewById(R.id.listView);
        String url =  new HttpRequest().url;
        PostAsyncTask get_request = new PostAsyncTask(this,list_view, name,json_obj.toString());
        get_request.execute(url+"/"+method);
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
