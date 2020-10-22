package com.example.graymatter.Model.Game.TowerOfHanoi;

import com.example.graymatter.Model.Game.Game;

import java.util.ArrayList;

/**
 * class to represent the game "Tower of Hanoi", where the player must move disks between three rods without putting a larger disk on a larger one.
 * the goal is to move all disks from the left rod to the right rod.
 */
public class TowerOfHanoi extends Game {

    private HanoiBoard board;
    private int level = 3;
    private boolean won;
    private int moves;
    private long startTime;
    private int gameTime;
    private boolean active;

    public TowerOfHanoi(){
        gameOver = true;
        active = false;
    }

    public void startGame(){
        gameOver = false;
        active = true;
        board = new HanoiBoard(level);
        moves = 0;
        won = false;
        startTime = System.currentTimeMillis();
    }

    public int endGame(){
        gameOver = true;
        active = false;
        if (won && level == 8) {
            return (10000 / moves) + (500 / gameTime);
        }
        else {
            return 0;
        }
    }

    /**
     * method handles player input
     * @param from Holds information about which rod a disk is being moved from
     * @param to Holds information about which rod a disk is being moved to
     */
    public void makeMove(HanoiRodPosition from, HanoiRodPosition to){
        if (!active){
            throw new RuntimeException("Attempted game event while no active game");
        }

        if (board.moveDisk(from, to)) {
            moves++;
            if (board.isWon()) {
                gameTime = (int) (System.currentTimeMillis() - startTime) / 1000;
                won = true;
            }
        }
    }

    /**
     * method to set the game's level (number of disks). startGame() must be called for change to apply.
     * @param level the number of disks to set the game to (number between 3 and 8, inclusive)
     */
    public void setLevel(int level){
        if (level > 2 && level < 9){
            this.level = level;
        }
        else {
            throw new IllegalArgumentException("Level must be between one and 8");
        }
    }

    /**
     * Alternate method to change level, increments by one.
     */
    public void incLevel(){
        if (level < 8){
            level++;
        }
    }

    /**
     * Alternate method to change level, decrements by one.
     */
    public void decLevel(){
        if (level > 3){
            level--;
        }
    }


    public boolean isWon(){
        return won;
    }

    public ArrayList<ArrayList<Integer>> getState(){
        return board.getState();
    }

}
