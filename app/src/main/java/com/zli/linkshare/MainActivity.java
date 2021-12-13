package com.zli.linkshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //NFC: Initialize attributes

    public final static String TAG = "nfc test";
    public static final String Write_Detected = "NFC NOT DETECTED";
    public static final String Write_Success = "TEXT WRITTEN SUCCESSFULLY";
    public static final String Write_Error = "ERROR DURING WRITTING, TRY AGAIN";

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter intentFilter;
    boolean writeMode;
    Tag myTag;
    Context context;


    Button add;
    EditText txt;
    ListView theListView;
    ArrayList<String> addLink = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise NfcAdapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //if Nfc is no available on the current Device
        if(nfcAdapter == null) {
            Toast.makeText(MainActivity.this, "Nfc is not available on this Device", Toast.LENGTH_SHORT).show();
            finish();
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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