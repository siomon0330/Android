package com.example.chuanli.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button button;
    boolean isActive = false;
    CountDownTimer countDownTimer;

    public void setTextView(int i){

        int minute = i / 60;
        int second = i % 60;
        String secondString = "";

        if(second < 10){
            secondString = "0" + second;
        }else{
            secondString = Integer.toString(second);
        }

        textView.setText(Integer.toString(minute) + ": " + secondString);
    }

    public void resetTimer(){
        seekBar.setProgress(30);
        textView.setText("0:30");
        isActive = false;
        seekBar.setEnabled(true);
        button.setText("Go!");
        countDownTimer.cancel();
    }


    public void  buttonPress(View view){
      if(isActive == false){

          isActive = true;
          seekBar.setEnabled(false);
          button.setText("Stop");
          countDownTimer = new CountDownTimer(seekBar.getProgress()*1000, 1000) {
              @Override
              public void onTick(long l) {
                  setTextView((int)l/1000);

              }

              @Override
              public void onFinish() {

                  MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                  mediaPlayer.start();
                  resetTimer();

              }
          };

          countDownTimer.start();

      }else{

            resetTimer();


      }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                setTextView(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }
}
