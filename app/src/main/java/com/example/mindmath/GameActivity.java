package com.example.mindmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        updatePoints();
    }
    public void startGame(View v)
    {
        Intent i = new Intent(this, GameStartActivity.class);
        /*
        sign == 0 - > "+"
        sign == 1 - > "-"
        sign == 2 - > "x"
        sign == 3 - > "/"
        */
        switch(((Button) v).getText().toString())
        {
            case "+":
                i.putExtra("sign", (byte) 0);
                break;
            case "-":
                i.putExtra("sign", (byte) 1);
                break;
            case "x":
                i.putExtra("sign", (byte) 2);
                break;
            default:
                i.putExtra("sign", (byte) 3);
        }
        startActivity(i);
//        finish();
    }

    public void updatePoints()
    {
        String str = "Last Game Points: " + MainActivity.lastGamePoints;
        ((TextView) findViewById(R.id.textViewLastGamePoints)).setText(str);
        str = "Session Record: " + MainActivity.sessionRecord;
        ((TextView) findViewById(R.id.textViewSessionRecord)).setText(str);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // update the points visually here, you update the actual values in the game start activity
        updatePoints();
    }
}