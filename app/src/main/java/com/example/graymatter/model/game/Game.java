package com.example.graymatter.model.game;

public abstract class Game implements GameInterface{
    protected boolean gameOver;
    private static String gameString;
    public Game(){
    }
    public boolean isGameOver(){
        return gameOver;
    }

    public static String getGameString(){
        return gameString;
    }
}
