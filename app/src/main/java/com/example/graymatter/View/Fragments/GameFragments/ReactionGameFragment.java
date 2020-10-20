package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.R;
import com.example.graymatter.ViewModel.ReactionTimeViewModel;

public class ReactionGameFragment extends Fragment{
    private ScreenState screenState;
    private TextView reactionTestDescription;
    private ReactionTimeViewModel reactionTimeVM;

    // Different states to determine what will happen when the screen is touched
    enum ScreenState
    {
        REACTION, RESULT, WAIT, START
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reaction_test, container, false);
        super.onCreate(savedInstanceState);
        reactionTimeVM = new ViewModelProvider(this).get(ReactionTimeViewModel.class);
        screenState = ScreenState.START;
        reactionTimeVM.init();

        reactionTimeVM.getIsWaiting().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                    //waiting Time is over and we should now let the user know that he can click
                    showReactionScreen();
                }
            }
        });

        reactionTestDescription = (TextView) view.findViewById(R.id.reactionTestDescription);

        // Listens on the clicks and then determines what to do depending on what state the screen is in
        reactionTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (screenState){
                    case START:
                    case RESULT:
                        reactionTimeVM.startReactionGame();
                        showWaitScreen();
                        break;
                    case REACTION:
                    case WAIT:
                        reactionTimeVM.endReactionTimeGame();
                        showResult(reactionTimeVM.getScore());
                    default:
                        break;
                }
            }
        });

        // TODO clicking on this should take the user to the main page
        //ImageView reactionTestClose = (ImageView) view.findViewById(R.id.reactionTestClose);
        return view;
    }
    // shows the wait screen, before the reaction test is supposed to happen

    public void showWaitScreen() {
        reactionTestDescription.setBackgroundColor(0xff5555e5);
        reactionTestDescription.setText("Press the screen when it turns white");
        screenState = ScreenState.WAIT;
    }

    // showing the reaction screen, change color to white
    public void showReactionScreen(){
        reactionTestDescription.setTextColor(0xff000000);
        reactionTestDescription.setText("NOW");
        reactionTestDescription.setBackgroundColor(0xffffffff);
        screenState = ScreenState.REACTION;
    }

    // showing the result and the screen is back to black, if the user pressed to early the game will let the user know
    public void showResult (int res)
    {
        screenState = ScreenState.RESULT;
        reactionTestDescription.setTextColor(0xffffffff);
        reactionTestDescription.setBackgroundColor(0xff5555e5);
        if (res <= 0)
        {
            reactionTestDescription.setText("You pressed to early");
        }
        else
        {
            reactionTestDescription.setText("Your reaction time was " + res + " ms");
        }
    }
}
