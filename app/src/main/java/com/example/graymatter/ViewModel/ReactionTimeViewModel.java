package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ReactionGame.ReactionTimeGame;

import java.util.Timer;
import java.util.TimerTask;

public class ReactionTimeViewModel extends ViewModel {
    private ReactionTimeGame reactionTimeGame;
    private UpdateViewTask task;
    // A timer that holds tasks
    private Timer timer;
    MutableLiveData<Boolean> mIsWaiting = new MutableLiveData<>();
    private int score = 0;

    public void init(){
        reactionTimeGame = new ReactionTimeGame();
        timer = new Timer();
    }

    public void startReactionGame(){
        reactionTimeGame.startGame();
        // New task that runs after waitTime
        task = new UpdateViewTask();
        mIsWaiting.setValue(true);
        timer.schedule(task, reactionTimeGame.getRandWaitTime());
    }
    public void endReactionTimeGame(){
        // removes task from the timer when
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
    // a class that represents what should happen when the wait time is over
    private class UpdateViewTask extends TimerTask {
        @Override
        public void run() {
            //sets startTime to current time
           reactionTimeGame.setStartTime(System.currentTimeMillis());
           mIsWaiting.postValue(false);

        }
    }
}
