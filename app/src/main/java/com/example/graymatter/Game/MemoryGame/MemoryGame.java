package com.example.graymatter.Game.MemoryGame;

import com.example.graymatter.Game.GameState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MemoryGame implements GameState {

    private MemoryGrid grid;
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
        int x = event.x;
        int y = event.y;

        grid.choose(x, y);

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

    public class MemoryInfoPackage {

        boolean gameOverInfo;
        ArrayList<MemoryGrid.TileState> gridInfo;
        int levelInfo;

        private MemoryInfoPackage() {
            gameOverInfo = gameOver;
            gridInfo = grid.toArrayList();
            levelInfo = level;
        }
    }

    public MemoryInfoPackage getInfo() {

        return new MemoryInfoPackage();

    }


}
