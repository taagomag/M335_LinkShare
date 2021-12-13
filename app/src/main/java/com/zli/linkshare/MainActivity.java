package com.zli.linkshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button add;
    ArrayList<String> addLink = new ArrayList<String>();
    EditText txt;
    ListView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (EditText)findViewById(R.id.linkInputText);
        show = (ListView)findViewById(R.id.linkListView);
        add = (Button)findViewById(R.id.addLink);

        add.setOnClickListener(v -> {
            String getInput = txt.getText().toString();
            if(URLUtil.isValidUrl(getInput)) {
                if (addLink.contains(getInput)) {
                    Toast.makeText(this, "This link has already been saved", Toast.LENGTH_LONG).show();
                }else{
                    addLink.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, addLink);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.linkInputText)).setText("https:// ");
                    Toast.makeText(this, "The URL has been saved", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "The URL you have entered is incorrect or missing", Toast.LENGTH_LONG).show();
            }
        });

    }

}