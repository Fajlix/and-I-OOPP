package com.example.graymatter.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Felix
 * class that represents the ViewModel for the Memory Game.
 */
public class MemoryGameViewModel extends ViewModel {
    private MemoryGame memoryGame;
    private int level = 0;
    private int lives = 0;
    private DelayedTask task;
    private Timer timer;
    //Mutable live data used to notify observers when data is changed
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MemoryGrid.TileState>> grid = new MutableLiveData<>();
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    /**
     * Initializes the VM with a new instance of a game and sets start values for some attributes
     */
    public void init(){
        memoryGame = new MemoryGame();
        gameStarted.setValue(false);
        gameOver.setValue(false);
        timer = new Timer();
    }

    /**
     * Starts a new memoryGame and changes gameStarted, visibility to true i.e notifies observers
     * and sets start values for some attributes.
     */
    public void startMemoryGame(){
        memoryGame.startGame();
        grid.setValue(memoryGame.getGridAsArrayList());
        lives = memoryGame.getLives();
        visibility.setValue(true);
        gameStarted.setValue(true);
        task = new DelayedTask();
        timer.schedule(task, 1000);
    }

    /**
     * This update method is called after each update from gui in this case each time a tile
     * has been clicked.
     * @param newGrid a boolean that represents if the grid is a new grid or the same as last update.
     */

    private void update(boolean newGrid){
        if (memoryGame.getGameOver()) {
            level = memoryGame.endGame();
            gameStarted.setValue(false);
            gameOver.setValue(true);
        }
        else {
            lives = memoryGame.getLives();
            memoryGame.getGridAsArrayList();
            visibility.setValue(newGrid);
            grid.setValue(memoryGame.getGridAsArrayList());
            if (visibility.getValue() != null && visibility.getValue())
            {
                task = new DelayedTask();
                timer.schedule(task, 1000);
            }
        }
    }

    /**
     *This method should be called from the gui that is being used when a tile has been clicked
     * @param number This method should be called from the gui that is being used when a tile
     *               has been clicked.
     */
    public void tileHasBeenClicked(int number){
        update(memoryGame.makeMove(number));
    }
    //Getter data, both used for observers as well as normal getters
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
    public int getLives(){
        return lives;
    }

    /**
     * a private class that represents what should happen when the wait time is over.
     * In this case the visibility of the grid should be false after the wait time.
     */

    private class DelayedTask extends TimerTask {
        @Override
        public void run() {
            //sets visibility of tiles to hidden
            visibility.postValue(false);
        }
    }
}
