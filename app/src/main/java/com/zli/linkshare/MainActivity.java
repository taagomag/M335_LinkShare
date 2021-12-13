package com.zli.linkshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //NFC: Initialize attributes
    NfcAdapter nftAdapter;
    PendingIntent pendingIntent;
    final static String TAG = "nfc test";

    Button add;
    EditText txt;
    ListView theListView;
    ArrayList<String> addLink = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise NfcAdapter


        ((EditText)findViewById(R.id.linkInputText)).setText("https:// ");
        txt = (EditText)findViewById(R.id.linkInputText);
        theListView = (ListView)findViewById(R.id.linkListView);
        add = (Button)findViewById(R.id.addLink);

        add.setOnClickListener(v -> {
            String getInput = txt.getText().toString();
            if(URLUtil.isValidUrl(getInput)) {
                if (addLink.contains(getInput)) {
                    Toast.makeText(this, "This link has already been saved", Toast.LENGTH_LONG).show();
                }else{
                    addLink.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, addLink);
                    theListView.setAdapter(adapter);
                    ((EditText)findViewById(R.id.linkInputText)).setText("https:// ");
                    Toast.makeText(this, "The URL has been saved", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "The URL you have entered is incorrect or missing", Toast.LENGTH_LONG).show();
            }
        });

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, LinkInformation.class);
                String itemValue = (String) theListView.getItemAtPosition(position);
                i.putExtra("linkValue", itemValue);
                startActivity(i);
                Toast.makeText(MainActivity.this, "item clicked" + theListView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
        });



    }

}