package com.example.graymatter.Model.Game.TowerOfHanoi;

import com.example.graymatter.Model.Game.Game;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        active = false;
        EventBus.getDefault().register(this);
    }

    public void startGame(){
        active = true;
        board = new HanoiBoard(level);
        moves = 0;
        won = false;
        startTime = System.currentTimeMillis();
    }

    public int endGame(){
        active = false;
        if (won && level == 8) {
            return 10000 - (700 / moves) - (300 / gameTime);
        }
        else {
            return 0;
        }
    }

    /**
     * method handles player input
     * @param event Holds information about which rods a disk is being moved from
     */
    @Subscribe
    public void onHanoiEvent(HanoiEvent event){
        if (!active){
            throw new RuntimeException("Attempted game event while no active game");
        }

        if (board.moveDisk(event.moveFrom, event.moveTo)) {
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
