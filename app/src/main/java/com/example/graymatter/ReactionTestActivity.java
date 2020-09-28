package com.example.graymatter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graymatter.Game.Game;
import com.example.graymatter.Game.GameObserver;
import com.example.graymatter.Game.GameState;
import com.example.graymatter.Game.ReactionTime;

public class ReactionTestActivity extends AppCompatActivity implements GameObserver {
    private ClickState clickState;
    private Game game;

    @Override
    public void update() {
        showReactionScreen();
    }

    // Different states to determine what will happen when the screen is touched
    enum ClickState
    {
        START_TIMER, STOP_TIMER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_test);
        // changes gamestate of game
        game = new Game();
        game.ChangeState(new ReactionTime(game));
        // adds this as a observer of the game
        game.addObserver(this);
        clickState = ClickState.START_TIMER;

        final TextView reactionTestDescription = findViewById(R.id.reactionTestDescription);
        // Listens on the clicks and then determines what to do depending on what state the game is in
        reactionTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOrStopGame();
            }
        });

        // clicking on this should take the user to the main page
        ImageView reactionTestClose = (ImageView) findViewById(R.id.reactionTestClose);
    }
    private void startOrStopGame(){
        switch (clickState)
        {
            case START_TIMER:
                game.StartGame();
                // shows the screen when you wait for the react test
                showWaitScreen();
                clickState = ClickState.STOP_TIMER;
                break;
            case STOP_TIMER:
                int res = game.StopGame();
                showResult(res);
                clickState = ClickState.START_TIMER;
                break;
        }
    }
    // shows the wait screen, before the reaction test is supposed to happen
    public void showWaitScreen() {
        final TextView reactionTestDescription = (TextView) findViewById(R.id.reactionTestDescription);
        reactionTestDescription.setText("Press the screen when it turns white");
    }

    // showing the reaction screen, change color to white
    public void showReactionScreen(){
        final TextView reactionTestDescription = (TextView) findViewById(R.id.reactionTestDescription);
        reactionTestDescription.setTextColor(0xff000000);
        reactionTestDescription.setText("NOW");
        reactionTestDescription.setBackgroundColor(0xffffffff);
    }

    // showing the result and the screen is back to black, if the user pressed to early the game will let the user know
    public void showResult (int res)
    {
        final TextView reactionTestDescription = (TextView) findViewById(R.id.reactionTestDescription);
        reactionTestDescription.setTextColor(0xffffffff);
        reactionTestDescription.setBackgroundColor(0xff000000);
        if (res <= 0)
        {
            reactionTestDescription.setText("DIN FANSKAP KLICKADE FÖR TIDIGT");
        }
        else
        {
            reactionTestDescription.setText("Your reaction time was " + res + " ms");
        }
    }
}
