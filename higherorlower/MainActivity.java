package com.example.chuanli.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Random random = new Random();
    int guessNumber = random.nextInt(20) + 1;

    public void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void guess(View view) {
        EditText number = (EditText) findViewById(R.id.number);
        int num = Integer.parseInt(number.getText().toString());

        Log.i("number", ""+guessNumber);
        if(num < guessNumber) {
            makeToast("Higher!");
        }else if(num > guessNumber) {
            makeToast("Lower!");
        }else {
            makeToast("You got the right number, let's try again!");
            guessNumber = random.nextInt(20) + 1;
        }

    }
}
