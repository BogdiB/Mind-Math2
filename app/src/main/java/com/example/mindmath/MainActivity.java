package com.example.mindmath;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    public static int sessionRecord = 0, lastGamePoints = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGameActivity(View v)
    {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    public static void updatePoints(int sR, int lGP) // sessionRecord, lastGamePoints
    {
        sessionRecord = sR;
        lastGamePoints = lGP;
    }

    public void quit(View v)
    {
        finish();
    }
}