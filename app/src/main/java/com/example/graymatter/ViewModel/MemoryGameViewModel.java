package com.example.graymatter.ViewModel;

        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;
        import androidx.lifecycle.ViewModel;

        import com.example.graymatter.Model.Game.MemoryGame.MemoryEvent;
        import com.example.graymatter.Model.Game.MemoryGame.MemoryGame;
        import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;

        import java.util.ArrayList;
        import java.util.Timer;
        import java.util.TimerTask;

//TODO: Show current level and lives

public class MemoryGameViewModel extends ViewModel {
    private MemoryGame memoryGame;
    private int level = 0;
    private DelayedTask task;
    private Timer timer;
    //Mutable live data used to notify observers when data is changed
    private MutableLiveData<Boolean> gameStarted = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MemoryGrid.TileState>> grid = new MutableLiveData<>();
    private MutableLiveData<Boolean> visibility = new MutableLiveData<>();

    //Initializes the VM with a new instance of a game and sets start values for some attributes
    public void init(){
        memoryGame = new MemoryGame();
        gameStarted.setValue(false);
        gameOver.setValue(false);
        timer = new Timer();
    }
    // Starts a new memoryGame and changes gameStarted, visibility to true i.e notifies observers
    //and sets start values for some attributes
    public void startVisualGame(){
        memoryGame.startGame();
        grid.setValue(memoryGame.getGridAsArrayList());
        visibility.setValue(true);
        gameStarted.setValue(true);
        task = new DelayedTask();
        timer.schedule(task, 1000);
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

    //This update method is called after each update from gui in this case each time a tile
    // has been clicked
    private void update(){
        if (memoryGame.getGameOver()) {
            level = memoryGame.endGame();
            gameStarted.setValue(false);
            gameOver.setValue(true);
        }
        else {
            memoryGame.getGridAsArrayList();
            visibility.setValue(memoryGame.getNewGrid());
            grid.setValue(memoryGame.getGridAsArrayList());
            if (visibility.getValue() != null && visibility.getValue())
            {
                task = new DelayedTask();
                timer.schedule(task, 1000);
            }
        }
    }

    //This method should be called from the gui that is being used when a tile has been clicked
    public void tileHasBeenClicked(int number){
        memoryGame.onMemoryEvent(new MemoryEvent(number));
        update();
    }
    // a class that represents what should happen when the wait time is over
    private class DelayedTask extends TimerTask {
        @Override
        public void run() {
            //sets visibility of tiles to hidden
            visibility.postValue(false);
        }
    }
}
