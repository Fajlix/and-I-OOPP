package com.example.graymatter;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graymatter.Game.GameObserver;

public class ChimpTestActivity extends AppCompatActivity implements GameObserver {

    @Override
    public void update() {
        // showReactionScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chimp_test);

        final ChimpTestActivity CTA = this;

        final TextView chimpTestDescription = findViewById(R.id.chimpTestDescription);
        chimpTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearScreen();
                GridView gridView = findViewById(R.id.chimpTestGrid);
                gridView.bringToFront();
                int[] test = {1, 0, 0, 1};
                gridView.setAdapter(new ChimpTestGridAdapter(CTA, test));
                gridView.setNumColumns(4);
                gridView.setVerticalSpacing(10);
                gridView.setHorizontalSpacing(50);

            }
        });

        // clicking on this should take the user to the main page
        ImageView reactionTestClose = (ImageView) findViewById(R.id.chimpTestClose);
    }

    public void ClearScreen ()
    {
        final TextView chimpTestDescription = findViewById(R.id.chimpTestDescription);
        chimpTestDescription.setText("");
        final ImageView iconNumber1 = findViewById(R.id.iconNumber1);
        final ImageView iconNumber2 = findViewById(R.id.iconNumber2);
        final ImageView iconNumber3 = findViewById(R.id.iconNumber3);
        final ImageView iconNumber4 = findViewById(R.id.iconNumber4);
        iconNumber1.setVisibility(View.GONE);
        iconNumber2.setVisibility(View.GONE);
        iconNumber3.setVisibility(View.GONE);
        iconNumber4.setVisibility(View.GONE);
    }
}
