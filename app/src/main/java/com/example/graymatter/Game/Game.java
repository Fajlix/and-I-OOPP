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
        if (gameState != null)
            gameState.StartGame();
    }
    public int StopGame(){
        if (gameState != null)
            return gameState.StopGame();
        return -1;
    }
    public void ChangeState(GameState gameState){
        this.gameState = gameState;
    }
    public GameState getGameState(){
        return gameState;
    }
}
