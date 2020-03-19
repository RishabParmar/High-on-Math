package com.example.parma.highonmath;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Start test module:
    // Design a minimalistic UI
    // On clicking the start button, make it 'gone' and make all the background elements appear

    // High on Math brief algorithm:
    // First layout the initial rough design of the UI
    // Deal with the timer logic next
    // Construct the random number quiz module
    // Next deal with the test again functionality
    // Clean up the code and finalize the UI, add animations, music and images

    // Timer module:
    // First construct a brief timer UI
    // Write the algorithm
    // Construct the side effects (On timer reaching 0, block the test UI and make the test again button visible)

    // Test module:
    // First design a rough grid UI for the different answer options (------------> In activity.xml, there is default cell padding for individual buttons,
    // to remove that, we just add a background and viola!..end to end crisp UI is served!)
    // Write the algorithm for random math expressions and bind them properly with the UI
    // Test the test module for correct execution of the expressions

    // Score keeper module:
    // Update the score keeper after every question is dealt with
    // Update the image of right or wrong with the answer too

    Button startTest;
    TextView timer;
    TextView scoreCard;
    TextView expression;
    GridLayout optionsGrid;
    Button options[] = new Button[5];
    MediaPlayer mp;
    Button testAgain;
    ImageView resultImage;
    long testTime = 30000L;
    int correctAnswers = 0;
    int expressionAnswer = 0;
    int totalQuestionsAttempted = 0;
    boolean testComplete = false;
    String operators[] = {"+", "-"};

    public void testAgain(View view) {
        // Reinitializing the test materials
        commenceTest(startTest);
        correctAnswers = 0;
        totalQuestionsAttempted = 0;
        testComplete = false;
        resultImage.setVisibility(View.INVISIBLE);

        testAgain.setVisibility(View.GONE);
    }

    public void generateExpressionAndOptions() {
        Random randomNumberGenerator = new Random();
        // Just selected the bounds on hunches, can be updated after testing around
        int num1 = randomNumberGenerator.nextInt(60);
        int num2 = randomNumberGenerator.nextInt(35);
        String operator = operators[randomNumberGenerator.nextInt(operators.length)];
        if(operator.equals("+")) { expressionAnswer = num1 + num2;}
        else if(operator.equals("-")) { expressionAnswer = num1 - num2; }
        String expressionText = "Q.)  " + String.valueOf(num1) + " " + operator + " " + String.valueOf(num2);
        expression.setText(expressionText);
        expression.setScaleX(0f);
        expression.setScaleY(0f);
        expression.animate().scaleX(1f).scaleY(1f).setDuration(300).start();

        // Creating random answers to be bound to the random options
        String randomAnswers[] = new String[5];
        // Setting the answer to a random tile/ option button
        int answerPosition = randomNumberGenerator.nextInt(3) + 1;
        Log.i("Answer position:", String.valueOf(answerPosition));
        randomAnswers[answerPosition] = String.valueOf(expressionAnswer);
        for(int _=1; _<=4; _++) {
            if(randomAnswers[_] == null) {
                randomAnswers[_] = String.valueOf(randomNumberGenerator.nextInt(70));
                Log.i(String.valueOf(_), randomAnswers[_]);
            }
        }

        // Binding the numbers to UI
        // loop from 1 to 4 because we have initialised the UI so that the options1/2/3/4 work properly in the method below
        for(int i = 1;i<=4;i++) { options[i].setText( randomAnswers[i]); }
    }

    public void clickOption(View view) {
        if(!testComplete) {
            Button clickedOption = (Button) view;
            int answer = Integer.parseInt(clickedOption.getText().toString());
            if(answer == expressionAnswer) {
                correctAnswers++;
                mp = MediaPlayer.create(this, R.raw.correct);
                mp.start();
                resultImage.setImageResource(R.drawable.correct);
            }else {
                mp = MediaPlayer.create(this, R.raw.wrong);
                mp.start();
                resultImage.setImageResource(R.drawable.wrong);
            }
            totalQuestionsAttempted++;
            String scoreString = String.valueOf(correctAnswers) + "/" + String.valueOf(totalQuestionsAttempted);
            scoreCard.setText(scoreString);
            resultImage.setVisibility(View.VISIBLE);
            generateExpressionAndOptions();
        }
    }

    public void startTestTimer() {
        new CountDownTimer(testTime, 1000) {

            @Override
            public void onTick(long l) {
                String secondsRemaining = String.valueOf(l/1000)+ "s";
                timer.setText(secondsRemaining);
                if(l<=5000) {
                    timer.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFinish() {
                // Disable further clicks to the play area and revive test again functionality
                testComplete = true;
                mp = MediaPlayer.create(MainActivity.this, R.raw.testover);
                mp.start();

                // Make the test again UI appear again
                testAgain.setVisibility(View.VISIBLE);
                resultImage.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    public  void commenceTest(View view) {
        // Here view is nothing but the start button object
        view.setVisibility(View.GONE);
        view.setEnabled(false);

        // Making the test UI visible
        timer.setVisibility(View.VISIBLE);
        timer.setY(-900f);
        timer.animate().translationY(0f).setDuration(500).start();
        scoreCard.setVisibility(View.VISIBLE);
        scoreCard.setText("0/0");
        scoreCard.setY(-900f);
        scoreCard.animate().translationY(0f).setDuration(500).start();
        expression.setVisibility(View.VISIBLE);
        optionsGrid.setVisibility(View.VISIBLE);
        optionsGrid.setScaleX(0f);
        optionsGrid.setScaleY(0f);
        optionsGrid.animate().scaleX(1f).scaleY(1f).setDuration(500).start();
        startTestTimer();

        // Start the test by providing the question and the options
        generateExpressionAndOptions();
    }

    public void initialisingUIElements() {
        startTest = findViewById(R.id.startTest);
        timer = findViewById(R.id.timer);
        scoreCard = findViewById(R.id.scoreCard);
        expression = findViewById(R.id.expression);
        optionsGrid = findViewById(R.id.optionsGrid);
        testAgain = findViewById(R.id.testAgain);
        resultImage = findViewById(R.id.resultImage);
        for(int i=1; i<=4; i++) {
            // The following line of code first fetches the ids of all the option buttons present in the test grid and then stores all of them
            // in an array of option buttons
            options[i] = findViewById(getResources().getIdentifier("option"+String.valueOf(i), "id", getPackageName()));
        }

        // Animating the app title
        TextView appTitle = findViewById(R.id.appTitle);
        appTitle.setY(500f);
        appTitle.animate().translationY(0f).setDuration(500).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialising the UI elements
        initialisingUIElements();
    }
}
