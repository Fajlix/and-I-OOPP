package com.example.graymatter.ViewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Game.MemoryGame.MemoryEvent;
import com.example.graymatter.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.Game.MemoryGame.MemoryTile;
import com.example.graymatter.Model.Game.ChimpGame.ChimpEvent;
import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;

import java.util.ArrayList;

public class VisualMemoryViewModel extends ViewModel {
    private MemoryGame memoryGame;
    private int level = 0;
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MemoryGrid.TileState>> grid = new MutableLiveData<>();
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    public void init(){
        memoryGame = new MemoryGame();
        gameStarted.setValue(false);
        gameOver.setValue(false);
        visibility.setValue(false);
    }

    public void startVisualGame(){
        memoryGame.startGame();
        grid.setValue(memoryGame.getGridAsArrayList());
        gameStarted.setValue(true);
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
            grid.setValue(memoryGame.getGridAsArrayList());
        }
    }


    public void tileHasBeenClicked(View v){
        ArrayList<MemoryGrid.TileState> numbers = grid.getValue();
        int res = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(v));
            {
                res = i;
                break;
            }
        }
        memoryGame.onMemoryEvent(new MemoryEvent(res));
        update();
    }
}
