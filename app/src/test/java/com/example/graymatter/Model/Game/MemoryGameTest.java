package com.example.graymatter.Model.Game;

import android.service.quicksettings.Tile;

import com.example.graymatter.Model.MemoryGame.MemoryEvent;
import com.example.graymatter.Model.MemoryGame.MemoryGame;
import com.example.graymatter.Model.MemoryGame.MemoryGrid;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MemoryGameTest {
    MemoryGame game;

    @Before
    public void setUp(){
        game = new MemoryGame();
        game.startGame();
    }

    @Test
    public void winGame(){
        ArrayList<MemoryGrid.TileState> grid = game.getGridAsArrayList();

        int firstLevel = game.getLevel();

        for (int i=0 ; i<grid.size() ; i++){
            if (grid.get(i)==MemoryGrid.TileState.CORRECTHIDDEN){
                EventBus.getDefault().post(new MemoryEvent(i));
            }
        }

        int secondLevel = game.getLevel();

        assertTrue(secondLevel-firstLevel==1);
    }

}
