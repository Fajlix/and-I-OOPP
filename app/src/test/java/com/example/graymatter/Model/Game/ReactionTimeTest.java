package com.example.graymatter.Model.Game;

import com.example.graymatter.Model.Game.ReactionGame.ReactionTime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReactionTimeTest {
    @Before
    public void setUp(){

    }

    @Test
    public void startAndStopTest() throws InterruptedException {
        Game game = new Game();
        GameState reactionTime = new ReactionTime(game);
        game.ChangeState(reactionTime);
        reactionTime.StartGame();
        int res = reactionTime.StopGame();
        assertEquals(res, -1);
        reactionTime.StartGame();
        Thread.sleep(ReactionTime.maxWaitTime + 1000);
        assertTrue(reactionTime.StopGame()>0);
    }
}