package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ChimpGame.ChimpEvent;
import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;

/**
 * @author Felix
 * class that represents the ViewModel for the chimp game.
 */

public class ChimpGameViewModel extends ViewModel {
    private ChimpGame chimpGame;
    private int score = 0;
    //Mutable live data used to notify observers when data is changed
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<int[]> grid = new MutableLiveData<>();
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    /**
     * Initializes the VM with a new instance of a game and sets start values for some attributes.
     */
    public void init(){
        chimpGame = new ChimpGame();
        gameOver.setValue(false);
        visibility.setValue(false);
    }

    /**
     * Starts a new chimpGame and changes gameStarted to true i.e notifies observers
     */
    public void startChimpGame(){
        chimpGame.startGame();
        grid.setValue(chimpGame.getBoard());
    }
    //Getters for the data, both used for observers as well as normal getters
    public LiveData<int[]> getGrid(){
        return grid;
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

    /**
     * This update method is called after each update from gui in this case each time a tile
     * has been clicked.
     * The method checks if the game has ended and if not it gets the updated board.
     */
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

    /**
     * This method should be called from the gui that is being used when a tile has been clicked
     * @param number the position of the tile in the grid array
     */
    public void tileHasBeenClicked(int number){
        chimpGame.onChimpEvent(new ChimpEvent(number));
        update();
    }

}
