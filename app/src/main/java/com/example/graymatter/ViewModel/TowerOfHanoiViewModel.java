package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.Game.TowerOfHanoi.TowerOfHanoi;
import com.example.graymatter.Model.dataAccess.DataAccess;

import java.util.ArrayList;

public class TowerOfHanoiViewModel extends ViewModel {
    TowerOfHanoi towerOfHanoi;
    private int score = 0;
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArrayList<Integer>>> board = new MutableLiveData<>();
    int level;

    private DataAccess dataAccess;

    public void init(DataAccess dataAccess){
        this.dataAccess = dataAccess;
        towerOfHanoi = new TowerOfHanoi();
    }

    public void startToHGame(){
        towerOfHanoi.startGame();
        towerOfHanoi.setLevel(getLevel());
        update();
    }

    public int getScore() {
        return score;
    }

    //This update method is called after each update from gui in this case each time a tile
    // has been clicked
    private void update(){
        if (towerOfHanoi.isWon()) {
            board.setValue(towerOfHanoi.getState());
            score = towerOfHanoi.endGame();
            gameOver.setValue(true);
            if(dataAccess.isLoggedIn()){
                dataAccess.storeGameSession(score, towerOfHanoi.getGameName());
            }
        }
        else {
            board.setValue(towerOfHanoi.getState());
        }
    }

    public LiveData<ArrayList<ArrayList<Integer>>> getBoard ()
    {
        return board;
    }

    //This method should be called from the gui that is being used when a tile has been clicked
    public void tileHasBeenClicked(HanoiRodPosition from, HanoiRodPosition to){
        towerOfHanoi.makeMove(from, to);
        update();
    }

    public void setLevel (int level)
    {
        this.level = level;
        towerOfHanoi.setLevel(level);
    }

    public int getLevel ()
    {
        return level;
    }


    public MutableLiveData<Boolean> getGameOver ()
    {
        return gameOver;
    }
}