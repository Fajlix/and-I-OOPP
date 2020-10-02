package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ChimpGame.ChimpEvent;
import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;

public class ChimpGameViewModel extends ViewModel {
    private ChimpGame chimpGame;
    private int score = 0;
    //Mutable live data used to notify observers when data is changed
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<int[]> grid = new MutableLiveData<>();
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    //Initializes the VM with a new instance of a game and sets start values for some attributes
    public void init(){
        chimpGame = new ChimpGame();
        gameStarted.setValue(false);
        gameOver.setValue(false);
        visibility.setValue(false);
    }
    // Starts a new chimpGame and changes gameStarted to true i.e notifies observers
    public void startChimpGame(){
        chimpGame.startGame();
        grid.setValue(chimpGame.getBoard());
        gameStarted.setValue(true);
    }
    //Getters for the data, both used for observers as well as normal getters
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
    public int getScore() {
        return score;
    }

    //This update method is called after each update from gui in this case each time a tile
    // has been clicked
    private void update(){
        if (chimpGame.getGameOver()) {
            score = chimpGame.endGame();
            gameOver.setValue(true);
        }
        else {
            grid.setValue(chimpGame.getBoard());
            visibility.setValue(chimpGame.getNumberVisibility());
        }
    }

    //This method should be called from the gui that is being used when a tile has been clicked
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
        chimpGame.onChimpEvent(new ChimpEvent(res));
        update();
    }

}
