package com.example.graymatter.Model.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Game implements GameInterface{
    private List<GameObserver> observers = new ArrayList<>();

    public Game(){
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
}
