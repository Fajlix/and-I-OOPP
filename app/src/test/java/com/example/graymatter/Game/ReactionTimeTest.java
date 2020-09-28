package com.example.graymatter.Game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReactionTimeTest {
    GameState reactionTime;
    @Before
    public void setUp(){
        Game game = new Game();
        reactionTime = new ReactionTime(game);
    }

    @Test
    public void startAndStopTest() throws InterruptedException {
        reactionTime.StartGame();
        int res = reactionTime.StopGame();
        assertEquals(res, -1);
        reactionTime.StartGame();
        Thread.sleep(ReactionTime.maxWaitTime + 100);
        assertTrue(reactionTime.StopGame()>0);
    }
}