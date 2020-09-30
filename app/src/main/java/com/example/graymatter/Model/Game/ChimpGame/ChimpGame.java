package com.example.graymatter.Model.Game.ChimpGame;

import com.example.graymatter.Model.Game.Game;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

public class ChimpGame extends Game {
    private int numberQty; //amount of boxes to choose from at current stage, also serves as score.
    private int[] board = new int[24]; // The locations on which the numbers can appear, zeroes indicate empty spaces
    private int nextNumber;
    private boolean numberVisibility;
    private boolean gameOver;

    public ChimpGame(){
        gameOver = true;
        EventBus.getDefault().register(this);
    }


    public void startGame(){
        gameOver = false;
        numberQty = 4;
        fillBoard();
        numberVisibility = true;
        notifyObservers();
    }

    public int endGame(){
        gameOver = true;
        return numberQty;
    }

    @Subscribe
    public void onChimpEvent(ChimpEvent event){
        if (gameOver) {
            throw new RuntimeException("Attempt to select tile after game over");
        }
        int clickedTile = event.tileCoordinate;
        if ( clickedTile<0 || clickedTile>23 ){
            throw new RuntimeException("Input out of bounds");
        }
        if (board[clickedTile] == 0){ //Empty space clicked, do nothing
            return;
        }
        else if (board[clickedTile] == nextNumber){ //Correct number clicked

            if ( nextNumber == numberQty){ //The number clicked is the last number in the sequence, new round
                numberQty++;
                if (numberQty >= 25)
                {
                    gameOver = true;
                }
                nextNumber = 1;
                fillBoard();
                numberVisibility = true;
            } else { //The number clicked is not the last, continue the sequence
                board[clickedTile] = 0;
                nextNumber++;
                numberVisibility = false;
            }
        }
        else { //Incorrect number clicked
            gameOver = true;
        }
            notifyObservers();
    }

    private void fillBoard(){
        clearBoard();
        int placement;
        for (int i = 1; i <= numberQty; i++){
            placement = (int)Math.round(Math.random()*23);
            while(board[placement] != 0){
                placement += 1;
                placement %= 24;
            }
            board[placement] = i;
        }
        nextNumber = 1;
    }

    private void clearBoard(){
        for (int i = 0; i<24; i++){
            board[i] = 0;
        }
    }

    public int[] getBoard(){
        if (gameOver){
            throw new RuntimeException("Attempt to get board after game over");
        }
        int[] boardCopy = Arrays.copyOf(board, 24); //Maybe unnecessary to copy primitive type, don't know, IDE seems to think so
        return boardCopy;
    }

    public boolean getNumberVisibility(){
        if (gameOver){
            throw new RuntimeException("Attempt to get number visibility after game over");
        }
        boolean numberVisibilityCopy = numberVisibility; //Maybe unnecessary to copy primitive type, don't know, IDE seems to think so
        return numberVisibilityCopy;
    }

    public boolean getGameOver(){
        boolean gameOverCopy = gameOver; //Maybe unnecessary to copy primitive type, don't know, IDE seems to think so
        return gameOverCopy;
    }
}
