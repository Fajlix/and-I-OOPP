package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Game.MemoryGame.MemoryEvent;
import com.example.graymatter.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Model.Game.ChimpGame.ChimpEvent;
import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;

public class VisualMemoryViewModel extends ViewModel {
    private MemoryGame memoryGame;
    private int level = 0;
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<int[]> grid = new MutableLiveData<>();
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    public void init(){
        memoryGame = new MemoryGame();
        gameStarted.setValue(false);
        gameOver.setValue(false);
        visibility.setValue(false);
    }

    public void startVisualGame(){
        memoryGame.startGame();
        grid.setValue(chimpGame.getBoard());
        gameStarted.setValue(true);
    }

    public LiveData<int[]> getGrid(){
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
            grid.setValue(chimpGame.getBoard());
            visibility.setValue(memoryGame.getVisibility());
        }
    }


    public void tileHasBeenClicked(int number){
        int [] numbers = grid.getValue();
        int res = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == number)
            {
                res = i;
                break;
            }
        }
        memoryGame.onMemoryEvent(new MemoryEvent(res));
        update();
    }
}
