package com.example.graymatter.Model.Game.ChimpGame;

import com.example.graymatter.Model.Game.Game;

import java.util.Arrays;

/**
 * Class to represent a game where the player must remember a sequence of tiles and then
 * select the tiles in order.
 */
public class ChimpGame extends Game {
    private int numberQty; //amount of boxes to choose from at current stage, also serves as score.
    private int[] board = new int[24]; // The grid on which the sequence tiles can appear, zeroes indicate empty spaces
    private int nextNumber;
    private boolean numberVisibility;
    private boolean gameOver;
    private int lives;


    private static final String gameName = "ChimpGame";


    public ChimpGame(){
        gameOver = true;
    }

    public void startGame(){
        lives = 3;
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

    /**
     * Method to handle changes in the game state based on player input.
     * @param clickedTile coordinate for the tile clicked by the player
     */
    public void makeMove(int clickedTile){
        if (gameOver) {
            throw new RuntimeException("Attempt to select tile after game over");
        }
        if ( clickedTile<0 || clickedTile>23 ){
            throw new RuntimeException("Input out of bounds");
        }
        if (board[clickedTile] == 0){ //Empty space clicked, do nothing
            return;
        }
        else if (board[clickedTile] == nextNumber){ //Correct number clicked

            if (nextNumber == numberQty){ //The number clicked is the last number in the sequence, new round
                numberQty++;
                if (numberQty >= 25)
                {
                    gameOver = true;
                }
                fillBoard();
            } else { //The number clicked is not the last, continue the sequence
                board[clickedTile] = 0;
                nextNumber++;
                numberVisibility = false;
            }
        }
        else { //Incorrect number clicked
            if (--lives == 0) gameOver = true;
            else fillBoard();
        }
            notifyObservers();
    }

    /**
     * Places sequence tiles on the game's grid.
     */
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
        numberVisibility = true;
    }

    /**
     * Removes all sequence tiles from the game's grid.
     */
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
        return numberVisibility;
    }

    public int getLives(){
        return lives;
    }

    public boolean getGameOver(){
        return gameOver;
    }


    public static String getGameName() {
        return gameName;
    }
}
