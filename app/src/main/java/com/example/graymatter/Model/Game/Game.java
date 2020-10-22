package com.example.graymatter.Model.Game;

public abstract class Game implements GameInterface{
    protected boolean gameOver;
    public Game(){
    }
    public boolean getGameOver(){
        return gameOver;
    }
}
