package com.example.chuanli.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                Log.i("hi", "hii");
                handler.postDelayed(this, 1000);

            }
        };

        handler.post(run);

*/


        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("cd",  "dfafada");
            }

            @Override
            public void onFinish() {

                Log.i("cd", "finished");
            }
        };

        countDownTimer.start();

    }
}
