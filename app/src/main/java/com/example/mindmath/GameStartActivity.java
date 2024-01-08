package com.example.mindmath;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameStartActivity extends AppCompatActivity
{
    private byte sign, questionsNr, questionsCurrent;
    /*
        sign == 0 - > "+"
        sign == 1 - > "-"
        sign == 2 - > "x"
        sign == 3 - > "/"
    */
    private int points, upperBoundNr, n1, n2; // upper bound can be modified
    private final int lowerBoundNr = -20, secondDegree = 15; // second degree is for multiplication and division
    private final Random rand = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        setInitial();
    }

    public void setInitial()
    {
        // setting the points to 0
        this.points = 0;
        ((TextView) findViewById(R.id.textViewPoints)).setText(R.string.points);
        // setting other relevant numbers to their defaults
        this.upperBoundNr = 50;
        this.questionsNr = (byte) 10;
        this.questionsCurrent = (byte) 0;
        // setting the sign and the first numbers
        this.sign = getIntent().getByteExtra("sign", (byte) 0);
        switch(this.sign)
        {
            case 0:
                // +
                ((TextView) findViewById(R.id.textViewSign)).setText(R.string.addition);
                this.n1 = this.lowerBoundNr + rand.nextInt((this.upperBoundNr - this.upperBoundNr / 10) + 1);
                this.n2 = this.n1 + rand.nextInt(this.upperBoundNr + 1);
                break;
            case 1:
                // -
                ((TextView) findViewById(R.id.textViewSign)).setText(R.string.substraction);
                this.n1 = (this.upperBoundNr / 2) + rand.nextInt((this.upperBoundNr / 2) + 1);
                this.n2 = this.lowerBoundNr + rand.nextInt(this.n1);
                break;
            case 2:
                // *
                ((TextView) findViewById(R.id.textViewSign)).setText(R.string.multiplication);
                // no lower bound because for the moment we don't want negative numbers in play
                // for upper bound it will randomly choose division between 3 and 4 bc I couldn't decide which one would be better
                this.n1 = rand.nextInt((this.upperBoundNr / (3 + rand.nextInt(1))) + 1);
                // maybe in future add a setting to change the range, so far I think this is fine
                this.n2 = this.n1 * rand.nextInt(this.secondDegree);
                break;
            default:
                // /
                ((TextView) findViewById(R.id.textViewSign)).setText(R.string.division);
                // no lower bound because for the moment we don't want negative numbers in play
                // maybe in future add a setting to change the range, so far I think this is fine
                // reversed from multiplication, because it's division:)
                // for upper bound it will randomly choose division between 3 and 4 bc I couldn't decide which one would be better
                this.n2 = rand.nextInt((this.upperBoundNr / (3 + rand.nextInt(1))) + 1);
                this.n1 = this.n2 * rand.nextInt(this.secondDegree);
        }

        // set the first set of numbers
        String ps = "Points: 0 / " + this.questionsNr;
        ((TextView) findViewById(R.id.textViewPoints)).setText(ps);
        ((TextView) findViewById(R.id.textViewNumber)).setText(String.valueOf(this.n1));
        ((TextView) findViewById(R.id.textViewEqualsTo)).setText(String.valueOf(this.n2));
    }

    public void next(View v)
    {
        // check if answer is not empty
        int answer;
        try
        {
            // get the answer
            EditText e = findViewById(R.id.editTextNumber);
            answer = Integer.parseInt(e.getText().toString());
            // remove the text from the EditText
            e.setText("", TextView.BufferType.EDITABLE);
        }
        catch (NullPointerException e)
        {
            Toast.makeText(getApplicationContext(),"No answer!",Toast.LENGTH_SHORT).show();
            return;
        }
        // update question number only if the answer is not empty
        ++this.questionsCurrent;
        // check the answer
        switch(this.sign)
        {
            case 0:
                // +
                if (this.n1 + answer == this.n2)
                    ++this.points;
                else
                {
                    end();
                    return;
                }
                break;
            case 1:
                // -
                if (this.n1 - answer == this.n2)
                    ++this.points;
                else
                {
                    end();
                    return;
                }
                break;
            case 2:
                // *
                if (this.n1 * answer == this.n2)
                    ++this.points;
                else
                {
                    end();
                    return;
                }
                break;
            default:
                // /
                if (this.n1 / answer == this.n2)
                    ++this.points;
                else
                {
                    end();
                    return;
                }
        }
        // update the points counter
        String ps = "Points: " + this.points + " / " + this.questionsNr;
        ((TextView) findViewById(R.id.textViewPoints)).setText(ps);
        // check if question limit has been reached
        // since this check is done after the answer has been verified, the questionsCurrent variable needs to be one bigger than the max
        if (this.questionsCurrent > this.questionsNr)
        {
            try
            {
                Thread.sleep(1000); // 1s
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            end(); // go to the stats activity and close this one
        }
        else
        {
            // check if the answer is correct and calculate new numbers
            switch(this.sign)
            {
                case 0:
                    // +
                    this.n1 = this.lowerBoundNr + rand.nextInt((this.upperBoundNr - this.upperBoundNr / 10) + 1);
                    this.n2 = this.n1 + rand.nextInt(this.upperBoundNr + 1);
                    break;
                case 1:
                    // -
                    this.n1 = (this.upperBoundNr / 2) + rand.nextInt((this.upperBoundNr / 2) + 1);
                    this.n2 = this.lowerBoundNr + rand.nextInt(this.n1);
                    break;
                case 2:
                    // *
                    // no lower bound because for the moment we don't want negative numbers in play
                    // for upper bound it will randomly choose division between 3 and 4 bc I couldn't decide which one would be better
                    this.n1 = rand.nextInt((this.upperBoundNr / (3 + rand.nextInt(1))) + 1);
                    // maybe in future add a setting to change the range, so far I think this is fine
                    this.n2 = this.n1 * rand.nextInt(this.secondDegree);
                    break;
                default:
                    // /
                    // no lower bound because for the moment we don't want negative numbers in play
                    // maybe in future add a setting to change the range, so far I think this is fine
                    // reversed from multiplication, because it's division:)
                    // for upper bound it will randomly choose division between 3 and 4 bc I couldn't decide which one would be better
                    this.n2 = rand.nextInt((this.upperBoundNr / (3 + rand.nextInt(1))) + 1);
                    this.n1 = this.n2 * rand.nextInt(this.secondDegree);
            }
            // set the new numbers
            ((TextView) findViewById(R.id.textViewNumber)).setText(String.valueOf(this.n1));
            ((TextView) findViewById(R.id.textViewEqualsTo)).setText(String.valueOf(this.n2));
        }
    }

    public void end()
    {
        // update the static points in the main activity
        MainActivity.lastGamePoints = this.points;
        if (this.points > MainActivity.sessionRecord)
            MainActivity.sessionRecord = this.points;
        // close this activity and send the user to the stats activity
//        Intent i = new Intent(this, StatsActivity.class);
//        startActivity(i);
        finish();
    }
}