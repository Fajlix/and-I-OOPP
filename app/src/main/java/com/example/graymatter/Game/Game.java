package com.example.graymatter.Game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game game;
    private GameState gameState;
    private List<GameObserver> observers = new ArrayList<>();

    private Game(){

    }
    public static Game getInstance(){
        if (game==null)
            game = new Game();
        return game;
    }
    public void addObserver(GameObserver gameObserver){
        observers.add(gameObserver);
    }
    public void removeObserver(GameObserver gameObserver){
        observers.remove(gameObserver);
    }
    public void notifyObservers(){
        for (GameObserver gameObserver: observers){
            gameObserver.update();
        }
    }

    public void StartGame(){
        gameState.StartGame();
    }
    public int StopGame(){
        return gameState.StopGame();
    }
    public void ChangeState(GameState gameState){
        this.gameState = gameState;
    }
}
