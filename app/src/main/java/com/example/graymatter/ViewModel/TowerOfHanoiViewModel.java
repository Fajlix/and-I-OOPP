package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TowerOfHanoiViewModel extends ViewModel {

    private int score = 0;


    public void init(){

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