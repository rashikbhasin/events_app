package com.example.cfit010.myapplicationevent;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAsyncTask extends AsyncTask<String ,Void,String> {

    private ListView mListData;
    private TextView mTextView;
    private String mJson_data;
    Activity mContext;


    public PostAsyncTask(Activity context,ListView listData, TextView text,String json_data){
        this.mListData = listData;
        this.mTextView = text;
        this.mJson_data = json_data;
        this.mContext = context;
    }


    protected String doInBackground(String... urls) {
        HttpRequest request = new HttpRequest();
        return request.doPost(urls[0],mJson_data);
    }

    protected void onPostExecute(String result)
    {
        ParseJSON json_obj = new ParseJSON();
        ArrayList<DataModel> data;// = new ArrayList<>();

        data = (ArrayList<DataModel>) json_obj.parse(result);
        String output;
        if(data.isEmpty()) {
            output = "No Result";
            this.mTextView.setText(output);
        }
        else {
            output = "";
            this.mTextView.setText(output);
        }
        CustomAdaptor adapter=new CustomAdaptor(this.mContext, data);
        mListData.setAdapter(adapter);
    }
}