package com.example.cfit010.myapplicationevent;

import android.os.AsyncTask;
import android.widget.TextView;

public class PostAsyncTask extends AsyncTask<String ,Void,String> {

    private TextView mTextData;
    private String mJson_data;


    public PostAsyncTask(TextView textData,String json_data){
        this.mTextData = textData;
        this.mJson_data = json_data;
    }


    protected String doInBackground(String... urls) {
        HttpRequest request = new HttpRequest();
        return request.doPost(urls[0],mJson_data);
    }

    protected void onPostExecute(String result)
    {
        ParseJSON json_obj = new ParseJSON();
        String output;
        if(result.equals("No Result"))
            output = result;

        else
            output = json_obj.parse(result);
        this.mTextData.setText(output);
    }
}