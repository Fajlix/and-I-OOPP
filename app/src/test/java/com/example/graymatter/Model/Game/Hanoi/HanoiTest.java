package com.example.graymatter.Model.Game.Hanoi;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiEvent;
import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.Game.TowerOfHanoi.TowerOfHanoi;

import org.greenrobot.eventbus.EventBus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HanoiTest {
    TowerOfHanoi game;




    @Before
    public void setUp(){
        game = new TowerOfHanoi();

        game.startGame();
    }

    @Test
    public void finishGame(){

        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.LEFT, HanoiRodPosition.RIGHT));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.LEFT, HanoiRodPosition.MIDDLE));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.RIGHT, HanoiRodPosition.MIDDLE));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.LEFT, HanoiRodPosition.RIGHT));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.MIDDLE, HanoiRodPosition.LEFT));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.MIDDLE, HanoiRodPosition.RIGHT));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.LEFT, HanoiRodPosition.RIGHT));

        assertTrue(game.isWon());
    }

    @Test
    public void invalidMove(){

        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.LEFT, HanoiRodPosition.MIDDLE));
        EventBus.getDefault().post(new HanoiEvent(HanoiRodPosition.LEFT, HanoiRodPosition.MIDDLE));

        ArrayList<ArrayList<Integer>> list = game.getState();
        assertTrue(list.get(0).size() == 2 && list.get(1).size() == 1);
    }

    @Test
    public void setLevel(){

        int level0 = game.getState().get(0).size();
        game.setLevel(5);
        game.startGame();
        int level1 = game.getState().get(0).size();
        game.incLevel();
        game.startGame();
        int level2 = game.getState().get(0).size();
        game.decLevel();
        game.startGame();
        int level3 = game.getState().get(0).size();

        assertTrue(level0 == 3 && level1 == 5 && level2 == 6 && level3 == 5);
    }

    @Test
    public void endGame(){

        int score = game.endGame();

        assertEquals(0, score);
    }

    @Test
    public void illegalArgumentTest(){
        try {
            game.setLevel(9);
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}
