package com.example.graymatter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.graymatter.Game.ReactionTime;

public class ReactionTestActivity extends Fragment {
    private ReactionTime reactionTime;
    private ClickState clickState;
    private TextView reactionTestDescription;

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
        reactionTime = new ReactionTime(this);
        clickState = ClickState.START_TIMER;

        reactionTestDescription = (TextView) view.findViewById(R.id.reactionTestDescription);

        // Listens on the clicks and then determines what to do depending on what state the screen is in
        reactionTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (clickState)
                {
                    case START_TIMER:
                        reactionTime.StartGame();
                        // shows the screen when you wait for the react test
                        showWaitScreen();
                        clickState = ClickState.STOP_TIMER;
                        break;
                    case STOP_TIMER:
                        int res = reactionTime.StopGame();
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
