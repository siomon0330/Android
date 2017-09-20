package com.example.chuanli.briantrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    TextView score;
    TextView sum;
    TextView result;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button go;
    Button playagain;
    ArrayList<Integer> list = new ArrayList<>();
    CountDownTimer countDownTimer;
    int times = 0;
    int correct = 0;
    int correctLocation;

    public void setTimer(){
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l/1000));

            }

            @Override
            public void onFinish() {

                result.setText("Your Score: " + correct + "/" + times);
                playagain.setVisibility(View.VISIBLE);
                disableButton();



            }
        };
        countDownTimer.start();
    }



    public void disableButton(){
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
    }
    public void enableButton(){
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
    }


    public void press(View view){
        if(view.getTag().toString().equals(String.valueOf(correctLocation))){
            correct++;
            result.setText("Correct!");
        }else{
            result.setText("Wrong!");
        }

        times++;
        score.setText(String.valueOf(correct) + "/" + String.valueOf(times));
        generateGame();

    }

    public void generateGame(){

        list.clear();
        Random random = new Random();
        int a = random.nextInt(21);
        int b = random.nextInt(21);
        sum.setText(String.valueOf(a) + "+ " + String.valueOf(b));
        correctLocation = random.nextInt(4);
        for(int i  = 0 ;  i < 4; i++){
            if(i == correctLocation){
                list.add(a+b);
            }else{
                int ran = random.nextInt(41);
                while(ran == a+b){
                    ran = random.nextInt(41);
                }
                list.add(ran);
            }
        }
        button0.setText(String.valueOf(list.get(0)));
        button1.setText(String.valueOf(list.get(1)));
        button2.setText(String.valueOf(list.get(2)));
        button3.setText(String.valueOf(list.get(3)));


    }

    public void playAgainPress(View view){

        timer.setText("30");
        correct = 0;
        times = 0;
        setTimer();
        score.setText("0/0");
        result.setText("");
        generateGame();
        playagain.setVisibility(View.INVISIBLE);
        enableButton();



    }

    public void setInvisible(){
        timer.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        sum.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);

    }

    public void setVisible(){
        timer.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        sum.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);


    }

    public void gopress(View view){

        go.setVisibility(View.INVISIBLE);
        setVisible();
        generateGame();
        setTimer();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);
        sum = (TextView) findViewById(R.id.sum);
        result = (TextView) findViewById(R.id.result);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playagain = (Button)findViewById(R.id.playagain);
        go = (Button) findViewById(R.id.go);
        go.setVisibility(View.VISIBLE);
        playagain.setVisibility(View.INVISIBLE);
        setInvisible();

    }
}











