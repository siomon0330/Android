package com.example.chuanli.picdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void change(View view){
        ImageView pic1 = (ImageView) findViewById(R.id.pic1);
        pic1.setImageResource(R.drawable.pic2);


    }

}
