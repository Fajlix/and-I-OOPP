package com.example.graymatter.Model.Game;

public abstract class Game implements GameInterface{
    protected boolean gameOver;
    private static String gameString;
    public Game(){
    }
    public boolean getGameOver(){
        return gameOver;
    }

    public static String getGameString(){
        return gameString;
    }
}
