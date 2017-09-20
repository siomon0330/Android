package com.example.chuanli.guesscelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();

    int correctLocation;
    int celebrity;
    String[] answers = new String[4];

    public class DownloadingInfo extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                String res = "";
                while(data != -1){
                    res = res + (char)data;
                    data = inputStreamReader.read();
                }

                return res;


            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }


            return null;
        }
    }

    public class DownloadPic extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return  bitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;

        }
    }


    public void generateGuess(){
        Random random = new Random();
        celebrity = random.nextInt(url.size());
        DownloadPic downloadPic = new DownloadPic();
        try {
            Bitmap bitmap = downloadPic.execute(url.get(celebrity)).get();
            imageView.setImageBitmap(bitmap);

        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }


        correctLocation = random.nextInt(4);
        for(int i = 0; i < 4; i++){
            if(i == correctLocation){
                answers[i] = name.get(celebrity);
            }else{
                answers[i] = name.get(random.nextInt(name.size()));
                while(answers[i] == name.get(celebrity)){
                    answers[i] = name.get(random.nextInt(name.size()));
                }
            }
        }

        button1.setText(answers[0]);
        button2.setText(answers[1]);
        button3.setText(answers[2]);
        button4.setText(answers[3]);



    }

    public void press(View view){

        if(view.getTag().equals(String.valueOf(correctLocation))){
            Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Wrong!It's" + name.get(celebrity),Toast.LENGTH_LONG).show();
        }


        generateGuess();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        DownloadingInfo downloadingInfo = new DownloadingInfo();

        String str = "";
        try {
            str = downloadingInfo.execute("http://www.posh24.se/kandisar").get();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        }


        String[] result = str.split("<div class=\"sidebarContainer\">");
        str = result[0];
        Pattern pattern = Pattern.compile("<img src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(str);

        while(matcher.find()){
            url.add(matcher.group(1));
        }

        pattern = Pattern.compile("alt=\"(.*?)\"/>");
        matcher = pattern.matcher(str);
        while(matcher.find()){
            name.add(matcher.group(1));
        }

        generateGuess();

    }
}
