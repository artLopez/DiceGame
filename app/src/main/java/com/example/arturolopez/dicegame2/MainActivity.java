package com.example.arturolopez.dicegame2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int userOverallScore = 0;
    public int userTurnScore = 0;
    public int cpuOverallScore = 0;
    public int cpuTurnScore = 0;
    private TextView score = null;
    private Button rollButton = null;
    private Button resetButton = null;
    private Button holdButton = null;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = (TextView) findViewById(R.id.scores);
        rollButton = (Button) findViewById(R.id.rollButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        holdButton = (Button) findViewById(R.id.holdButton);

        score.setText(getOverallScore());

        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(roleDie() == 1){
                    computerTurn();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetGame();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setOverallScore();
                computerTurn();
            }
        });

    }

    public int roleDie() {
        final Random randomGenerator = new Random();
        final ImageView diceView = (ImageView) findViewById(R.id.currentDice);
        int randomNum = randomGenerator.nextInt(6) + 1;
        int imageResource = getResources().
                getIdentifier("dice" + randomNum, "drawable", getPackageName());

        diceView.setImageDrawable(getResources().getDrawable(imageResource));
        setTurnScore(randomNum);
        return randomNum;
    }

    public void setTurnScore(int dice_num ){
        if(rollButton.isEnabled()){
            if(dice_num != 1){
                userTurnScore += dice_num;
            }
            else {
                userTurnScore = 0;
            }
            score.setText(getOverallScore() + " your turn score: " + userTurnScore);
        }
        else{
            if(dice_num != 1)
                cpuTurnScore += dice_num;
            else
                cpuTurnScore = 0;

            score.setText(getOverallScore() + " CPU turn score: " + cpuTurnScore);
        }
    }

    public void resetGame(){
       userOverallScore = 0;
       userTurnScore = 0;
       cpuOverallScore = 0;
       cpuTurnScore = 0;
       score.setText(getOverallScore());
    }

    public void setOverallScore(){
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        score.setText(getOverallScore());
    }

    public void computerTurn(){
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        int roleNum = roleDie();

        while(cpuTurnScore < 20 && roleNum != 1 ){
            roleNum = roleDie();
            timerRunnable.run();
        }
        cpuOverallScore += cpuTurnScore;
        cpuTurnScore = 0;
        score.setText(getOverallScore());
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
    }

    public String getOverallScore(){
        return "Your score: " + userOverallScore + " Computer score: " + cpuOverallScore;
    }
}

