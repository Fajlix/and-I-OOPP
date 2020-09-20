package com.example.graymatter.Game;

import android.os.Handler;

import com.example.graymatter.ReactionTestActivity;

public class ReactionTime implements GameState{
    private long startTime;
    private long endTime;
    private long waitTime;
    //max wait time in ms
    private int maxWaitTime = 3000;
    //min wait time in ms
    private int minWaitTime = 500;
    private Handler handler = new Handler();
    private boolean running = false;
    public ReactionTime(){
        startTime = 0;
    }


    //Call this to start a new reactionTest
    public void StartGame() {

        // a random time between min- and maxWaitTime
        waitTime = Math.round(Math.random() * maxWaitTime) + minWaitTime;
        //This is used to make sure test is not stopped before waitTime is over
        running = true;
        // New thread that runs after waitTime
        //TODO WE SHOULD NOT HAVE THREADS HERE
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //sets startTime to current time
                startTime = System.currentTimeMillis();
                if (running) {
                    // when the reaction text shows the screen to react to
                    Game.getInstance().notifyObservers();
                }
            }
        },waitTime);

    }
    //Call this to stop the reactionTest returns -1 if clicked to early
    public int StopGame(){
            int result;
            //makes sure that "click now" is not printed if clicked to early
            running = false;
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
}
