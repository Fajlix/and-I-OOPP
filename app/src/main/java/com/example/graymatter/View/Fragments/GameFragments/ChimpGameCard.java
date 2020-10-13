package com.example.graymatter.View.Fragments.GameFragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graymatter.R;

public class ChimpGameCard extends AppCompatActivity {
    TextView cardFront;
    TextView cardBack;
    int number;

    AnimatorSet frontAnim;
    AnimatorSet backAnim;
    boolean isFront = true;

    public ChimpGameCard(){
        cardFront = findViewById(R.id.card_front);
        cardBack = findViewById(R.id.back_card);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chimp_game_card);
        Intent intent = getIntent();
        cardFront = findViewById(R.id.card_front);
        cardBack = findViewById(R.id.back_card);
        cardFront.setText(String.valueOf(number));

        Float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        cardFront.setCameraDistance(8000 * scale);
        cardBack.setCameraDistance(8000 * scale);

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.front_animation);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),R.animator.back_animation);


    }

    public boolean getIsFront ()
    {
        return isFront;
    }

    public void setIsFront (boolean isFront) {
        this.isFront = isFront;
    }
}
