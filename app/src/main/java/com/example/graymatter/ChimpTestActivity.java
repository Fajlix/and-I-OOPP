package com.example.graymatter;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graymatter.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Game.Game;
import com.example.graymatter.Game.GameObserver;

public class ChimpTestActivity extends AppCompatActivity implements GameObserver {
    private TextView chimpTestDescription;
    private Game game;

    @Override
    public void update() {
        // showReactionScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chimp_test);

        // changes the state of game
        game = new Game();
        game.ChangeState(new ChimpGame(game));
        // adds this as a observer of the game
        game.addObserver(this);

        final ChimpTestActivity CTA = this;
        final ChimpGame CG = new ChimpGame(game); // idk bout creating 2 ChimpGame?????
        game.ChangeState(CG);

        chimpTestDescription = (TextView) findViewById(R.id.chimpTestDescription);

        // when the user press the screen, the test should start
        chimpTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearScreen();
                game.StartGame();
                ShowBoard(CTA, CG);

            }
        });

        // clicking on this should take the user to the main page
        ImageView reactionTestClose = (ImageView) findViewById(R.id.chimpTestClose);
    }

    // clears the screen of all teh text and images to show the test
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

    public void ShowBoard (ChimpTestActivity CTA, ChimpGame CG)
    {
        GridView gridView = findViewById(R.id.chimpTestGrid);
        gridView.bringToFront();
        gridView.setAdapter(new ChimpTestGridAdapter(CTA, CG.getBoard()));
        gridView.setNumColumns(4);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(50);
    }
}
