package com.example.graymatter.model.game.reactiongame;

import com.example.graymatter.model.game.Game;

/**
 * @author Felix
 * Class that represents the model for the game Reaction Time.
 */

public class ReactionTimeGame extends Game {
    private long startTime;
    private long endTime;
    private long randWaitTime;
    //max wait time in ms
    private static int maxWaitTime;
    //min wait time in ms
    private static int minWaitTime;

    private final static String gameString = "ReactionTimeGame";
    /**
     * Only constructor for ReactionTime. No parameters
     */
    public ReactionTimeGame(){
        gameOver = true;
        maxWaitTime = 3000;
        minWaitTime = 500;
        startTime = 0;
        endTime = 0;
    }

    /**
     * Method that starts a new reaction Game.
     * A random wait time is set which is used to make the user wait for a certain amount of time
     * before reacting.
     */
    public void startGame() {
        gameOver = false;
        // a random time between min- and maxWaitTime
        randWaitTime = Math.round(Math.random() * maxWaitTime) + minWaitTime;
    }

    /**
     * Method that stops the test. Should be called when the player reacts.
     * @return Returns the time it took for the player to react. Returns -1 if reacted to early.
     */
    //Call this to stop the reactionTest returns -1 if clicked to early
    public int endGame(){
            gameOver = true;
            int result;
            endTime = System.currentTimeMillis();
            //If starTime is not set the player reacted before waitTime is over and reacted to early.
            if (startTime == 0) {
                return -1;
            }
            result = (int) (endTime - startTime);
            // resets startTime
            startTime = 0;
            return result;
    }

    /**
     * Method that sets the startTime which represents the time when the player should react.
     * Should be called after the random wait time.
     * @param startTime long that is the time the test actually started in milliseconds.
     */
    public void setStartTime(long startTime){
        this.startTime = startTime;
    }

    /**
     *
     * @return random time before test starts.
     */
    public long getRandWaitTime(){
        return randWaitTime;
    }

    public static String getGameString(){
        return gameString;
    }
}