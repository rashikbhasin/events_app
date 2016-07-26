package com.example.cfit010.myapplicationevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json=new JSONObject();

                try {
                    EditText ed = (EditText)findViewById(R.id.deleteId);
                    json.put("event_id", ed.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String serverURL = "http://192.168.3.3:8080/deleted";
                TextView answer = (TextView)findViewById(R.id.deletedItem);
                PostJson postJson = new PostJson(json, answer);
                postJson.execute(serverURL);

            }
        });
    }
}
