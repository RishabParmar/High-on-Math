package com.example.parma.highonmath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

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
    // First design a rough grid UI for the different answer options
    // Write the algorithm for random math expressions and bind them properly with the UI
    // Test the test module for correct execution of the expressions

    // Score keeper module:
    // Update the score keeper after every question is dealt with
    // Update the image of right or wrong with the answer too

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
