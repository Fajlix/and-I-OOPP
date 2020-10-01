package com.example.graymatter.Game.MemoryGame;

import com.example.graymatter.Game.GameState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MemoryGame implements GameState {

    private MemoryGrid grid; // The grid on which the selectable tiles are located
    private int level;
    private int lives;
    private boolean gameOver;

    public MemoryGame(){
        gameOver = true;
        EventBus.getDefault().register(this);
    }

    public void StartGame(){
        gameOver = false;
        level = 1;
        lives = 3;
        grid = new MemoryGrid(level);
    }

    public int StopGame(){
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
    //TODO: notify observers
    }

    /*
    *
    *  Following internal class and method used by GUI to get information to display
    *
     */
    public class MemoryInfoPackage { // Class with copies of relevant data for View/Controller

        public boolean gameOverInfo;
        public ArrayList<MemoryGrid.TileState> gridInfo;
        public int levelInfo;

        private MemoryInfoPackage() {
            gameOverInfo = gameOver;
            gridInfo = grid.toArrayList();
            levelInfo = level;
        }
    }

    public MemoryInfoPackage getInfo() { // Method for View/Controller to receive needed information

        return new MemoryInfoPackage();

    }


}
