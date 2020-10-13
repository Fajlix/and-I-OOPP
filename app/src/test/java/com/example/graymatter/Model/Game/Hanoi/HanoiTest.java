package com.example.graymatter.Model.Game.Hanoi;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiEvent;
import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.Game.TowerOfHanoi.TowerOfHanoi;

import org.greenrobot.eventbus.EventBus;
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


}
