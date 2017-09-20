package com.example.chuanli.timestable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    SeekBar seekBar;

    public void generateTimeTable(int time){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            list.add(Integer.toString(i * time));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setMax(20);
        seekBar.setProgress(10);

        int time = 10;
        generateTimeTable(time);



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int time;
                if(i <= 1){
                    seekBar.setProgress(1);
                    time = 1;
                }else{
                    time = i;
                }

                generateTimeTable(time);


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

















