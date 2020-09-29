package com.example.graymatter.View.Fragments.GameFragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graymatter.R;

public class ChimpGameCard extends AppCompatActivity {
    TextView numberText;
    int number;

    public ChimpGameCard(int pos, int number){
        numberText = findViewById(R.id.cardNumber);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chimp_game_card);
        numberText = findViewById(R.id.cardNumber);
        Intent intent = getIntent();
        numberText.setText(String.valueOf(number));
    }
}
