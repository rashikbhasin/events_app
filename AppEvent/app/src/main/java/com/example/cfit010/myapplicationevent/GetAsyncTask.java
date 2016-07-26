package com.example.cfit010.myapplicationevent;

import android.os.AsyncTask;
import android.widget.TextView;

public class GetAsyncTask extends AsyncTask<String ,Void,String> {

    private TextView mTextData;


    public GetAsyncTask(TextView textData){
        this.mTextData = textData;
    }


    protected String doInBackground(String... urls) {
        HttpRequest request = new HttpRequest();
        return request.doGet(urls[0]);
    }

    protected void onPostExecute(String result)
    {
        ParseJSON json_obj = new ParseJSON();
        String output;
        output = json_obj.parse(result);
        this.mTextData.setText(output);
    }
}