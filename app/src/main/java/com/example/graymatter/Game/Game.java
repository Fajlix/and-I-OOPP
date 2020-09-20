package com.example.graymatter.Game;

public class Game {
    private static Game game;
    private GameState gameState;

    private Game(){

    }
    public static Game getInstance(){
        if (game==null)
            game = new Game();
        return game;
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
