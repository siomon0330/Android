package com.example.chuanli.gridlayoutdemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void buttonClick(View view){

        int id = view.getId();
        String myid = "";
        myid = getResources().getResourceEntryName(id);
        int resourceid = getResources().getIdentifier(myid, "raw", "com.example.chuanli.gridlayoutdemo");
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resourceid);
        mediaPlayer.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
