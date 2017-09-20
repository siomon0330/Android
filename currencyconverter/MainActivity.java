package com.example.chuanli.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void convert(View view){

        EditText money = (EditText) findViewById(R.id.moneyText);
        Double amount = Double.valueOf(money.getText().toString()) * 6.9;
        Toast.makeText(this, "¥" + String.format("%.2f", amount), Toast.LENGTH_SHORT).show();


    }
}
