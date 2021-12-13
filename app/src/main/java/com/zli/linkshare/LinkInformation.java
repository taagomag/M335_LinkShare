package com.zli.linkshare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LinkInformation extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_information);

        TextView urlValue = (TextView)findViewById(R.id.urlValue);

        String value;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                urlValue.setText("no url");
            } else {
                value= extras.getString("linkValue");
                urlValue.setText(value);
            }
        }
    }
}