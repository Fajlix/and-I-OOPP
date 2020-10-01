package com.example.graymatter.ViewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.MemoryGame.MemoryEvent;
import com.example.graymatter.Model.MemoryGame.MemoryGame;
import com.example.graymatter.Model.MemoryGame.MemoryGrid;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VisualMemoryViewModel extends ViewModel {
    private MemoryGame memoryGame;
    private int level = 0;
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MemoryGrid.TileState>> grid = new MutableLiveData<>();
    private DelayedTask task;
    private Timer timer;
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    public void init(){
        memoryGame = new MemoryGame();
        gameStarted.setValue(false);
        gameOver.setValue(false);
        timer = new Timer();
    }

    public void startVisualGame(){
        memoryGame.startGame();
        grid.setValue(memoryGame.getGridAsArrayList());
        gameStarted.setValue(true);
        task = new DelayedTask();
        timer.schedule(task, 1000);
    }
    public int getGridSize(){
        return MemoryGrid.size(memoryGame.getLevel());
    }

    public LiveData<ArrayList<MemoryGrid.TileState>> getGrid(){
        return grid;
    }
    public LiveData<Boolean> getGameStarted(){
        return gameStarted;
    }
    public LiveData<Boolean> getVisibility(){
        return visibility;
    }
    public LiveData<Boolean> getGameOver(){
        return gameOver;
    }
    public int getLevel() {
        return level;
    }

    private void update(){
        if (memoryGame.getGameOver()) {
            level = memoryGame.endGame();
            gameOver.setValue(true);
        }
        else {
            visibility.setValue(memoryGame.getNewGrid());
            grid.setValue(memoryGame.getGridAsArrayList());
            if (visibility.getValue() != null && visibility.getValue())
            {
                task = new DelayedTask();
                timer.schedule(task, 1000);
            }
        }
    }


    public void tileHasBeenClicked(int number){

        memoryGame.onMemoryEvent(new MemoryEvent(number));
        update();
    }
    private class DelayedTask extends TimerTask {
        @Override
        public void run() {
            //sets visibility of tiles to hidden
            visibility.postValue(false);
        }
    }
}
