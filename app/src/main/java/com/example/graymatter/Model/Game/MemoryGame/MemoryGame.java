package com.example.graymatter.Model.Game.MemoryGame;

import com.example.graymatter.Model.Game.Game;

import java.util.ArrayList;

/**
 *  This class describes the logic for a game where the player is shown a grid of tiles for a second,
 *  and then has to select which tiles in the grid were coloured.
 *
 *  it is designed to test the player's visual memory.
 */
public class MemoryGame extends Game{

    private MemoryGrid grid; // The grid on which the selectable tiles are located
    private int level;
    private int lives;
    private boolean gameOver;

    public MemoryGame(){
        gameOver = true;
    }

    public void startGame(){
        gameOver = false;
        level = 1;
        lives = 3;
        grid = new MemoryGrid(level);
    }

    public int endGame(){
        gameOver = true;
        return level;
    }

    /**
     * Method to respond to player choosing a tile, called through an event bus
     * @param tileCoordinate holds data about which tile has been chosen
     */
    public boolean makeMove(int tileCoordinate){
        boolean newGrid = false;
        if (gameOver) {
            throw new RuntimeException("Attempt to select tile after game over");
        }

        grid.choose(tileCoordinate);

        switch (grid.getStatus()){
            case WON: //All correct tiles chosen
                newGrid = true;
                grid = new MemoryGrid(++level);
                break;
            case LOST: //Too many wrong answers on current grid
                newGrid = true;
                lives -= 1;
                if (lives==0){
                    gameOver = true;
                    break;
                }
                grid = new MemoryGrid(level);
                break;
            default:
                break;
        }
        return newGrid;
    }

    /*
    *
    *  Following methods used by GUI to get data to display
    *
     */

    public ArrayList<MemoryGrid.TileState> getGridAsArrayList(){
        return grid.toArrayList();
    }


    public MemoryGrid.TileState getTileState(int tileCoordinate){
        return grid.getTileState(tileCoordinate);
    }

    public int getLevel(){
        return level;
    }

    public int getLives() {
        return lives;
    }

    public boolean getGameOver(){
        return gameOver;
    }
}
