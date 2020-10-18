package com.example.graymatter.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiEvent;
import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.Game.TowerOfHanoi.TowerOfHanoi;

public class TowerOfHanoiViewModel extends ViewModel {
    TowerOfHanoi towerOfHanoi;
    private int score = 0;
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    int level;
    //ToHDraw toHDraw;

    public void init(){
        towerOfHanoi = new TowerOfHanoi();
        gameOver.setValue(false);
        drawStartDisks(getLevel());
    }

    public void startToHGame(){
        towerOfHanoi.startGame();
        towerOfHanoi.setLevel(getLevel());
    }

    public int getScore() {
        return score;
    }

    //This update method is called after each update from gui in this case each time a tile
    // has been clicked
    private void update(){
        if (towerOfHanoi.isWon()) {
            score = towerOfHanoi.endGame();
            gameOver.setValue(true);
        }
        else {
            towerOfHanoi.getState();
        }
    }

    //This method should be called from the gui that is being used when a tile has been clicked
    public void tileHasBeenClicked(HanoiRodPosition from, HanoiRodPosition to){
        towerOfHanoi.onHanoiEvent(new HanoiEvent(from, to));
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


    public void drawStartDisks (int numberOfDisks)
    {
        //toHDraw = new ToHDraw(, 100, 100, numberOfDisks);
    }

    public MutableLiveData<Boolean> getGameOver ()
    {
        return gameOver;
    }
}