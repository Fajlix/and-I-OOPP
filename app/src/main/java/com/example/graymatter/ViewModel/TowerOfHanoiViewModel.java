package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.GameStrings;
import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.Game.TowerOfHanoi.TowerOfHanoi;
import com.example.graymatter.Model.dataAccess.DataAccess;

import java.util.ArrayList;

/**
 * @author Viktor Felix
 * class that represents the ViewModel for the tower of hanoi game.
 */
public class TowerOfHanoiViewModel extends ViewModel {
    TowerOfHanoi towerOfHanoi;
    private int score = 0;
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ArrayList<Integer>>> board = new MutableLiveData<>();
    int level;

    private DataAccess dataAccess;

    /**
     * Initializes the ViewModel with a new instance of a game
     */
        public void init(DataAccess dataAccess){
            this.dataAccess = dataAccess;
            towerOfHanoi = new TowerOfHanoi();
        }

    /**
     * Starts the towerOfHanoi instance and get the level the user has chosen and updates the
     * new data from the model
     */
    public void startToHGame(){
        towerOfHanoi.startGame();
        towerOfHanoi.setLevel(getLevel());
        update();
    }

    public int getScore() {
        return score;
    }

    /**
     * This method is called from the GUI when a rod has been moved
     * The method checks if the game has is done, if not the game updates the rods.
     */
    private void update(){
        if (towerOfHanoi.isWon()) {
            board.postValue(towerOfHanoi.getState());
            score = towerOfHanoi.endGame();
            gameOver.setValue(true);
            if(dataAccess.isLoggedIn()){
                dataAccess.storeGameSession(score, GameStrings.getTowerString());
            }
        }
        else {
            board.postValue(towerOfHanoi.getState());
        }
    }

    public LiveData<ArrayList<ArrayList<Integer>>> getBoard ()
    {
        return board;
    }

    /**
     * When a rod has been clicked this method is being called, te get the data from the model
     * @param from is the parameter for the rod the disk has moved from
     * @param to is the parameter for the rod the disk has moved to
     */
    public void tileHasBeenClicked(HanoiRodPosition from, HanoiRodPosition to){
        towerOfHanoi.makeMove(from, to);
        update();
    }

    /**
     * Sets the level of the Tower of Hanoi game the user has chosen
     * @param level is the level
     */
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