package com.example.chuanli.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class Task extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String[] objects) {
            String res = "";
            URL url;
            HttpURLConnection httpURLConnection = null;
            try{

                url = new URL(objects[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data != -1){
                    char current = (char)data;
                    res = res+current;
                    data = reader.read();
                }
                return res;



            }catch (Exception e){
                return "fail";
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("aaa","sajfhakshfkasdhfld");
        Task task = new Task();
        String str ="";
        try{
            str = task.execute("https://www.ecowebhosting.co.uk/").get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.print(str);
    }
}
