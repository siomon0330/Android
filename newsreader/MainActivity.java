package com.example.chuanli.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> context = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleId INT, title VARCHAR, content VARCHAR)");

        ListView listView = (ListView) findViewById(R.id.ListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, title);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("content", context.get(i));
                startActivity(intent);

            }
        });

       updateListView();
        Download download = new Download();
        try {
            download.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateListView(){
        Cursor c = database.rawQuery("SELECT * FROM articles", null);
        int contentIndex = c.getColumnIndex("content");
        int titleIndex = c.getColumnIndex("title");

        if(c.moveToFirst()){
            title.clear();
            context.clear();

            do{
                title.add(c.getString(titleIndex));
                context.add(c.getString(contentIndex));


            }while(c.moveToNext());

         arrayAdapter.notifyDataSetChanged();

        }

    }

    public class Download extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection;
            String result = "";

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while(data != -1){
                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }
                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = 20;
                if(jsonArray.length() < 20){
                    numberOfItems = jsonArray.length();
                }

                database.execSQL("DELETE  FROM articles");

                for(int i = 0; i < numberOfItems; i++){
                    String articleId = jsonArray.getString(i);
                    URL articleUrl = new URL("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty");
                    String articleInfo = "";
                    httpURLConnection = (HttpURLConnection) articleUrl.openConnection();
                    inputStream = httpURLConnection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    data = inputStreamReader.read();
                    while(data != -1){
                        char character = (char) data;
                        articleInfo += character;
                        data = inputStreamReader.read();
                    }
                    JSONObject jsonObject = new JSONObject(articleInfo);
                   // Log.i("info", articleInfo);
                  //  Log.i("in", jsonObject.toString());

                    if(!jsonObject.isNull("title") && !jsonObject.isNull("url")) {

                        String articleTitle = jsonObject.getString("title");
                        String articleLink = jsonObject.getString("url");


                        URL url2 = new URL(articleLink);
                        httpURLConnection = (HttpURLConnection)url2.openConnection();
                        inputStream = httpURLConnection.getInputStream();
                        inputStreamReader = new InputStreamReader(inputStream);
                        String articleContent = "";
                        data = inputStreamReader.read();
                        while(data != -1){
                            char character = (char) data;
                            articleContent += character;
                            data = inputStreamReader.read();
                        }

                        Log.i("info", articleContent);

                        String sql = "INSERT INTO articles (articleId, title, content) VALUES (?, ?, ?)";
                        SQLiteStatement statement = database.compileStatement(sql);
                        statement.bindString(1, articleId);
                        statement.bindString(2, articleTitle);
                        statement.bindString(3, articleContent);
                        statement.execute();


                    }
                }





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           updateListView();
        }
    }
}














