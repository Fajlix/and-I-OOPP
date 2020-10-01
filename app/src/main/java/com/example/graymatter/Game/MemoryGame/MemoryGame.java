package com.example.graymatter.Game.MemoryGame;

import com.example.graymatter.Model.Game.Game;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MemoryGame extends Game{

    private MemoryGrid grid; // The grid on which the selectable tiles are located
    private int level;
    private int lives;
    private boolean gameOver;

    public MemoryGame(){
        gameOver = true;
        EventBus.getDefault().register(this);
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

    @Subscribe
    public void onMemoryEvent(MemoryEvent event){
        if (gameOver) {
            throw new RuntimeException("Attempt to select tile after game over");
        }
        int tileCoordinate = event.tileCoordinate;


        grid.choose(tileCoordinate);

        switch (grid.getStatus()){
            case WON:
                grid = new MemoryGrid(++level);
                break;
            case LOST:
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
        notifyObservers();
    }

    /*
    *
    *  Following internal class and methods used by GUI to get information to display
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

    public boolean getGameOver(){
        return gameOver;
    }
}
