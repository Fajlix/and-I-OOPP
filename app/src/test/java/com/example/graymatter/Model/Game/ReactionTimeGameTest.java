package com.example.graymatter.Model.Game;

import com.example.graymatter.Model.Game.ReactionGame.ReactionTimeGame;
import com.example.graymatter.ViewModel.ReactionTimeViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.TimerTask;

import static org.junit.Assert.*;

public class ReactionTimeGameTest {
    private TimerTask task;
    private int sleepMillis;
    @Before
    public void setUp(){
        sleepMillis = 1000;
    }

    @Test
    public void reactionTimeCorrect() throws InterruptedException {
        ReactionTimeGame reactionTime = new ReactionTimeGame();
        reactionTime.startGame();
        reactionTime.setStartTime(System.currentTimeMillis());
        Thread.sleep(sleepMillis);
        assertTrue(Math.abs(sleepMillis - reactionTime.endGame()) < sleepMillis/10);
    }
    @Test
    public void reactionTimeTooFast() throws InterruptedException {
        ReactionTimeGame reactionTime = new ReactionTimeGame();
        reactionTime.startGame();
        assertEquals(reactionTime.endGame(), -1);
    }
}