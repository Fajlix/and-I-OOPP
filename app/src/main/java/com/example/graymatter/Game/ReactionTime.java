package com.example.graymatter.Game;

import java.util.Timer;
import java.util.TimerTask;

public class ReactionTime implements GameState{
    private long startTime;
    private long endTime;
    private long waitTime;
    private Game game;
    //max wait time in ms
    public static int maxWaitTime = 3000;
    //min wait time in ms
    public static int minWaitTime = 500;
    // A timer that holds tasks
    private Timer timer;
    //
    private UpdateViewTask runningTask;
    public ReactionTime(Game game){
        startTime = 0;
        timer = new Timer();
        this.game = game;
    }
    //Call this to start a new reactionTest
    public void StartGame() {

        // a random time between min- and maxWaitTime
        waitTime = Math.round(Math.random() * maxWaitTime) + minWaitTime;
        //This is used to make sure test is not stopped before waitTime is over
        // New task that runs after waitTime
        runningTask = new UpdateViewTask(game);
        timer.schedule(runningTask,waitTime);
    }
    //Call this to stop the reactionTest returns -1 if clicked to early
    public int StopGame(){
            int result;
            // removes task from the timer when
            if (runningTask != null)
            {
                runningTask.cancel();
            }
            //makes sure that "click now" is not printed if clicked to early
            endTime = System.currentTimeMillis();
            //If starTime is not set than button is clicked before waitTime is over
            if (startTime == 0) {
                result = -1;
            } else {
                result = (int) (endTime - startTime);
            }
            // resets startTime
            startTime = 0;
            // Showing your result and changing the screen
            return result;
    }
    //TODO DOES THIS COUNT AS THREAD?? SAME PROBLEM?
    private class UpdateViewTask extends TimerTask {
        // a class that represents what should happen when the wait time is over
        private Game game;
        public UpdateViewTask(Game game){
            this.game = game;
        }
        @Override
        public void run() {
            //sets startTime to current time
            startTime = System.currentTimeMillis();
            // when the reaction text shows the screen to react to
            game.notifyObservers();
        }
    }
}