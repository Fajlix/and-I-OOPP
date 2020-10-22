package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ReactionGame.ReactionTimeGame;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Felix
 * class that represents the ViewModel for the reaction game.
 */

public class ReactionTimeViewModel extends ViewModel {
    private ReactionTimeGame reactionTimeGame;
    private UpdateViewTask task;
    // A timer that holds tasks
    private Timer timer;
    private MutableLiveData<Boolean> mIsWaiting = new MutableLiveData<>();
    private int score = 0;

    /**
     * Initializes the ViewModel with a new instance of a game and sets start values for some
     * attributes.
     */

    public void init(){
        reactionTimeGame = new ReactionTimeGame();
        timer = new Timer();
    }

    /**
     * Starts a new reactionGame and starts a timer task which runs after randWaitTime
     * It also sets the Mutable live data boolean waiting to true to notify observers that the
     * View should be waiting for the time it should display their reactNow gui
     */
    public void startReactionGame(){
        reactionTimeGame.startGame();
        // New task that runs after waitTime
        task = new UpdateViewTask();
        mIsWaiting.setValue(true);
        timer.schedule(task, reactionTimeGame.getRandWaitTime());
    }

    public void endReactionTimeGame(){
        // removes task from the timer if the game ended to early so that the run method in the
        // timer task is called one time to much.
        if (task != null)
            task.cancel();
        score = reactionTimeGame.endGame();
    }
    public int getScore(){
        return score;
    }
    public LiveData<Boolean> getIsWaiting(){
        return mIsWaiting;
    }
    /**
     * a private class that represents what should happen when the wait time is over.
     * In this case sets the starttime of the reamtiontime Game and posts that the wait time is over.
     */
    private class UpdateViewTask extends TimerTask {
        @Override
        public void run() {
            //sets startTime to current time
           reactionTimeGame.setStartTime(System.currentTimeMillis());
           mIsWaiting.postValue(false);

        }
    }
}
