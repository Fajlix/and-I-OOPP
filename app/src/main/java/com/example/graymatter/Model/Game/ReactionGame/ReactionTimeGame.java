package com.example.graymatter.Model.Game.ReactionGame;

import com.example.graymatter.Model.Game.Game;

public class ReactionTimeGame extends Game {
    private long startTime;
    private long endTime;
    private long randWaitTime;
    //max wait time in ms
    private static int maxWaitTime;
    //min wait time in ms
    private static int minWaitTime;
    public ReactionTimeGame(){
        maxWaitTime = 3000;
        minWaitTime = 500;
        startTime = 0;
        endTime = 0;
    }
    //Call this to start a new reactionTest
    public void startGame() {

        // a random time between min- and maxWaitTime
        randWaitTime = Math.round(Math.random() * maxWaitTime) + minWaitTime;
        //This is used to make sure test is not stopped before waitTime is over
        // New task that runs after waitTime
    }
    //Call this to stop the reactionTest returns -1 if clicked to early
    public int endGame(){
            int result;
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
    public void setStartTime(long startTime){
        this.startTime = startTime;
    }
    public long getRandWaitTime(){
        return randWaitTime;
    }
}