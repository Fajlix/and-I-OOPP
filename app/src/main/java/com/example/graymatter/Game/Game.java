package com.example.graymatter.Game;

public class Game {
    private GameState gameState;

    public void StartGame(){

    }
    public int StopGame(){
        return 0;
    }
    public void ChangeState(GameState gameState){
        this.gameState = gameState;
    }
}
