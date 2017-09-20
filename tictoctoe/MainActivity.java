package com.example.chuanli.tictoctoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // 0 stands for black, 1 stands for white
    int player = 0;
    int[] gameStates = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winnerPositions = {{0,1,2}, {0,3,6}, {0,4,8}, {3,4,5}, {1,4,7},{2,4,6},{2,5,8},{6,7,8}};
    boolean isActive = true;

    public void click(View view){

        ImageView counter = (ImageView) view;
        int tag = Integer.parseInt(counter.getTag().toString());

        if(gameStates[tag] == 2 && isActive == true) {
            counter.setTranslationY(-1000f);
            gameStates[tag] = player;

            if (player == 0) {

                player = 1;
                counter.setImageResource(R.drawable.black);

            } else {

                player = 0;
                counter.setImageResource(R.drawable.white);

            }

            counter.animate().translationYBy(1000f).setDuration(100);





            for(int[] winnerPosition : winnerPositions) {
                if (gameStates[winnerPosition[0]] == gameStates[winnerPosition[1]]
                        && gameStates[winnerPosition[1]] == gameStates[winnerPosition[2]]
                        && gameStates[winnerPosition[0]] != 2) {

                    //someone wins!

                    isActive = false;
                    String message = "Black";
                    if (gameStates[winnerPosition[0]] == 1) {
                        message = "White";
                    }

                    EditText winner = (EditText) findViewById(R.id.winnerMessag);
                    winner.setText(message + " has won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
                    layout.setVisibility(View.VISIBLE);


                }
            }


            boolean gameover = true;
            for(int i = 0; i < gameStates.length; i++){
                System.out.print(gameStates[i]);
                if (gameStates[i] == 2){
                    gameover = false;
                }
            }

            if(gameover){
                EditText winner = (EditText)findViewById(R.id.winnerMessag);
                winner.setText("it is a tie! paly again!");
                LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
                layout.setVisibility(View.VISIBLE);
            }


        }

    }

    public void buttonClick(View view){

        isActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        layout.setVisibility(View.INVISIBLE);
        player = 0;
        for(int i = 0; i <gameStates.length; i++){
            gameStates[i] = 2;
        }
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < grid.getChildCount(); i++){
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
