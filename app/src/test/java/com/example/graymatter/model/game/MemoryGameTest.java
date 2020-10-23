package com.example.graymatter.model.game;

import com.example.graymatter.model.game.memoryGame.MemoryGame;
import com.example.graymatter.model.game.memoryGame.MemoryGrid;
import com.example.graymatter.model.game.memoryGame.MemoryTile;

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
                game.makeMove(i);
            }
        }

        int secondLevel = game.getLevel();

        assertTrue(secondLevel-firstLevel==1);
    }

    @Test
    public void loseGame(){

        int lives = game.getLives();

        while (!game.isGameOver()) {
            ArrayList<MemoryGrid.TileState> grid = game.getGridAsArrayList();
            int wrongs = 0;
            int i = 0;
            while (wrongs < 4) {
                if (grid.get(i) == MemoryGrid.TileState.INCORRECTHIDDEN) {
                    game.makeMove(i);
                    wrongs++;
                }
                i++;
            }
            lives--;
            if (lives != game.getLives()) {
                fail();
            }
        }

        assertTrue(game.isGameOver());
    }

    @Test
    public void copyTile(){
        MemoryTile tile1 = new MemoryTile(true);
        MemoryTile tile2 = new MemoryTile(tile1);
        boolean first = tile1.tileEquals(tile2);
        tile1.markChosen();
        tile2 = new MemoryTile(tile1);
        boolean second = tile1.tileEquals(tile2);
        tile1 = new MemoryTile(false);
        tile2 = new MemoryTile(tile1);
        boolean third = tile1.tileEquals(tile2);
        tile1.markChosen();
        tile2 = new MemoryTile(tile1);
        boolean fourth = tile1.tileEquals(tile2);

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
