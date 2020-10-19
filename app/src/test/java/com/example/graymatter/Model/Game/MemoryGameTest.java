package com.example.graymatter.Model.Game;

import com.example.graymatter.Model.Game.MemoryGame.MemoryEvent;
import com.example.graymatter.Model.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.Model.Game.MemoryGame.MemoryTile;

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

    @Test
    public void loseGame(){

        int lives = game.getLives();

        while (!game.getGameOver()) {
            ArrayList<MemoryGrid.TileState> grid = game.getGridAsArrayList();
            int wrongs = 0;
            int i = 0;
            while (wrongs < 4) {
                if (grid.get(i) == MemoryGrid.TileState.INCORRECTHIDDEN) {
                    game.onMemoryEvent(new MemoryEvent(i));
                    wrongs++;
                }
                i++;
            }
            lives--;
            if (lives != game.getLives()) {
                fail();
            }
        }

        assertTrue(game.getGameOver());
    }

    @Test
    public void copyTile(){
        MemoryTile tile1 = new MemoryTile(true);
        MemoryTile tile2 = new MemoryTile(tile1);
        boolean first = tile1.equals(tile2);
        tile1.markChosen();
        tile2 = new MemoryTile(tile1);
        boolean second = tile1.equals(tile2);
        tile1 = new MemoryTile(false);
        tile2 = new MemoryTile(tile1);
        boolean third = tile1.equals(tile2);
        tile1.markChosen();
        tile2 = new MemoryTile(tile1);
        boolean fourth = tile1.equals(tile2);

        assertTrue(first && second && third && fourth);
    }

    @Test
    public void getState(){
        ArrayList<MemoryGrid.TileState> grid = game.getGridAsArrayList();

        for (int i = 0; i<grid.size(); i++){

            if (game.getTileState(i) != grid.get(i)) fail();

        }

        assertTrue(true);
    }

}
