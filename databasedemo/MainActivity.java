package com.example.chuanli.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      try {
          SQLiteDatabase database = this.openOrCreateDatabase("Events", MODE_PRIVATE, null);
          database.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");
          database.execSQL("INSERT INTO events (name, year) VALUES ('war', 1900)");
          database.execSQL("INSERT INTO events (name, year) VALUES ('birth', 1994)");

          Cursor c = database.rawQuery("SELECT * FROM events", null);
          int nameIndex = c.getColumnIndex("name");
          int yearIndex = c.getColumnIndex("year");
          c.moveToFirst();
          while (c != null) {
              Log.i("name: ", c.getString(nameIndex));
              Log.i("year: ", "" + ""+c.getInt(yearIndex));
              c.moveToNext();

          }

      }catch ( Exception e){
          e.printStackTrace();
      }


    }
}
