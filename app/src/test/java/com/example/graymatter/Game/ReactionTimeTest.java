package com.example.graymatter.Game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReactionTimeTest {
    GameState reactionTime;
    @Before
    public void setUp() throws Exception {
        reactionTime = new ReactionTime();
    }

    @Test
    public void startAndStopTest() throws InterruptedException {
        reactionTime.StartGame();
        int res = reactionTime.StopGame();
        assertEquals(res, -1);
        reactionTime.StartGame();
        System.out.println("1");
        Thread.sleep(ReactionTime.maxWaitTime);
        assertTrue(reactionTime.StopGame()>0);
    }
}