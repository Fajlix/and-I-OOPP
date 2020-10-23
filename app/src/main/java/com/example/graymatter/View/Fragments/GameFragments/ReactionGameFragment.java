package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.R;
import com.example.graymatter.View.FragmentChangeListener;
import com.example.graymatter.ViewModel.ReactionTimeViewModel;

/**
 * @author Viktor Felix
 * the class that represents the fragment for Chimp Game
 */
public class ReactionGameFragment extends Fragment{
    private ScreenState screenState;
    private TextView reactionTestDescription;
    private ReactionTimeViewModel reactionTimeVM;
    private FragmentChangeListener listener;

    // Different states to determine what will happen when the screen is touched
    enum ScreenState
    {
        REACTION, RESULT, WAIT, START
    }

    /**
     * Initializes the start screen, and the updates it depending on what the user does.
     * @return returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reaction_test, container, false);
        super.onCreate(savedInstanceState);
        listener = (FragmentChangeListener)getContext();
        reactionTimeVM = new ViewModelProvider(this).get(ReactionTimeViewModel.class);
        screenState = ScreenState.START;
        reactionTimeVM.init(listener.getDataAccess());

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

        return view;
    }

    /**
     * This screen is shown when the user wait on the react screen
     */
    public void showWaitScreen() {
        reactionTestDescription.setBackgroundColor(0xff5555e5);
        reactionTestDescription.setText("Press the screen when it turns white");
        screenState = ScreenState.WAIT;
    }

    /**
     * When this screen shows to let the user know to react
     */
    public void showReactionScreen(){
        reactionTestDescription.setTextColor(0xff000000);
        reactionTestDescription.setText("NOW");
        reactionTestDescription.setBackgroundColor(0xffffffff);
        screenState = ScreenState.REACTION;
    }

    /**
     * When the test is over this method is called to show the react time
     * @param res is the time the user got
     */
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
