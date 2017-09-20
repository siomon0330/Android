package com.example.chuanli.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.chuanli.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> lantitude = new ArrayList<>();
        ArrayList<String> longtitude = new ArrayList<>();
        places.clear();
        lantitude.clear();
        longtitude.clear();
        locations.clear();

        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            lantitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lantitude", ObjectSerializer.serialize(new ArrayList<String>())));
            longtitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lantitude", ObjectSerializer.serialize(new ArrayList<String>())));


        } catch (IOException e) {
            e.printStackTrace();
        }

        if(places.size() > 0 && lantitude.size() > 0 && longtitude.size()>0){
            if(places.size() == lantitude.size() && lantitude.size() == longtitude.size()){
                for(int i = 0; i < lantitude.size(); i++){
                    locations.add(new LatLng(Double.parseDouble(lantitude.get(i)), Double.parseDouble(longtitude.get(i))));
                }
            }
        }else {

            places.add("add places you like");
            locations.add(new LatLng(0, 0));
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("number", i);
                startActivity(intent);

            }
        });


    }
}
