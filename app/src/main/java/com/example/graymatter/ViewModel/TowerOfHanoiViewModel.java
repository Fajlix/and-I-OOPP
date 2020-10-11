package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;


public class TowerOfHanoiViewModel extends ViewModel {
    private ChimpGame chimpGame;
    private int score = 0;
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();


    public void init(){

    }
    // Starts a new chimpGame and changes gameStarted to true i.e notifies observers
    public void startChimpGame(){
        chimpGame.startGame();
    }

    public LiveData<Boolean> getGameStarted(){
        return gameStarted;
    }

    public LiveData<Boolean> getGameOver(){
        return gameOver;
    }
    public int getScore() {
        return score;
    }

    private void update(){

    }

    //This method should be called from the gui that is being used when a tile has been clicked
    public void tileHasBeenClicked(int number){

    }
}
