package com.zli.linkshare;

import static com.zli.linkshare.App.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.*;


import java.net.URL;
import java.nio.channels.Channel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //NFC: Initialize attributes
    private NotificationManagerCompat notificationManager;
    private String notificationTitle;

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    Button add;
    EditText txt;
    ListView theListView;
    ArrayList<String> addLink = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationManager = NotificationManagerCompat.from(this);
        notificationTitle = "This is your received url -->";

        //Initialise NfcAdapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //if Nfc is no available on the current Device
        if(nfcAdapter == null) {
            Toast.makeText(MainActivity.this, "Nfc is not available on this Device", Toast.LENGTH_SHORT).show();
            finish();
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        ((EditText)findViewById(R.id.linkInputText)).setText("https://");
        txt = (EditText)findViewById(R.id.linkInputText);
        theListView = (ListView)findViewById(R.id.linkListView);
        add = (Button)findViewById(R.id.addLink);

        add.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_HEAVY_CLICK));
            } else {
                vibrator.vibrate(200);
            }

            String getInput = txt.getText().toString();
            if(URLUtil.isValidUrl(getInput)) {
                if (addLink.contains(getInput)) {
                    Toast.makeText(this, "This link has already been saved", Toast.LENGTH_LONG).show();
                }else{
                    addLink.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, addLink);
                    theListView.setAdapter(adapter);
                    ((EditText)findViewById(R.id.linkInputText)).setText("https://");
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            sendNotification(getInput);
                        }
                    }, 5000);
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

    public void sendNotification(String url) {
        String title = notificationTitle;
        String message = url;


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_link)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[] {100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);


    }

}