package com.example.chuanli.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void click(View view) {

        //ImageView pic1 = (ImageView) findViewById(R.id.pic1);
        ImageView pic2 = (ImageView) findViewById(R.id.pic2);


       // pic1.animate().alpha(0f).setDuration(3000);


        pic2.animate().translationXBy(1000f).translationYBy(1000f).rotation(3600).setDuration(2000);






    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView pic2 = (ImageView) findViewById(R.id.pic2);
        pic2.animate().translationX(-1000f).translationY(-1000f);

    }
}
