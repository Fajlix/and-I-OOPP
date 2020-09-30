package com.example.graymatter.Model.Game;

import com.example.graymatter.Model.Game.ReactionGame.ReactionTimeGame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReactionTimeGameTest {
    @Before
    public void setUp(){

    }

    @Test
    public void startAndStopTest() throws InterruptedException {
        ReactionTimeGame reactionTime = new ReactionTimeGame();
        reactionTime.startGame();
        int res = reactionTime.endGame();
        assertEquals(res, -1);
        reactionTime.startGame();
        Thread.sleep(reactionTime.getRandWaitTime() + 1000);
        assertTrue(reactionTime.endGame()>0);
    }
}