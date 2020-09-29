package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.graymatter.Model.Game.Game;
import com.example.graymatter.Model.Game.GameObserver;
import com.example.graymatter.Model.Game.ReactionGame.ReactionTime;
import com.example.graymatter.R;

public class ReactionGameFragment extends Fragment implements  GameObserver {
    private ClickState clickState;
    private TextView reactionTestDescription;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reaction_test, container, false);
        super.onCreate(savedInstanceState);
        // changes gameState of game
        game = new Game();
        game.ChangeState(new ReactionTime(game));
        // adds this as a observer of the game
        game.addObserver(this);
        clickState = ClickState.START_TIMER;

        reactionTestDescription = (TextView) view.findViewById(R.id.reactionTestDescription);

        // Listens on the clicks and then determines what to do depending on what state the screen is in
        reactionTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });

        // clicking on this should take the user to the main page
        ImageView reactionTestClose = (ImageView) view.findViewById(R.id.reactionTestClose);



        return view;
    }
    // shows the wait screen, before the reaction test is supposed to happen

    public void showWaitScreen() {
        reactionTestDescription.setText("Press the screen when it turns white");
    }

    // showing the reaction screen, change color to white
    public void showReactionScreen(){
        reactionTestDescription.setTextColor(0xff000000);
        reactionTestDescription.setText("NOW");
        reactionTestDescription.setBackgroundColor(0xffffffff);
    }

    // showing the result and the screen is back to black, if the user pressed to early the game will let the user know
    public void showResult (int res)
    {
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
