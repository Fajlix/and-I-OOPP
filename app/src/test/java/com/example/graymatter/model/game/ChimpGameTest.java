package com.example.graymatter.model.game;

import com.example.graymatter.model.game.chimpGame.ChimpGame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class ChimpGameTest {
    ChimpGame chimpGame;
    int[] board;
    @Before
    public void setUp(){
        chimpGame = new ChimpGame();
        chimpGame.startGame();
        board = chimpGame.getBoard();
    }


    @Test
    public void buildBoard(){
        ArrayList<Integer> numbers = new ArrayList<>();

        int zeroes = 0;
        boolean one = false;
        boolean two = false;
        boolean three = false;
        boolean four = false;

        for ( int i : board ){

            switch(i){
                case 0:
                    zeroes++;
                    break;
                case 1:
                    one = true;
                    break;
                case 2:
                    two = true;
                    break;
                case 3:
                    three = true;
                    break;
                case 4:
                    four = true;
                    break;
                default:
                    fail();
            }
        }
        assertTrue( zeroes == 20 && one && two && three && four );
    }

    @Test
    public void completeLevel(){

        int posOfOne=-1;
        int posOfTwo=-1;
        int posOfThree=-1;
        int posOfFour=-1;

        for ( int i = 0 ; i < 24 ; i++ ) {
            switch (board[i]){
                case 1:
                    posOfOne = i;
                    break;
                case 2:
                    posOfTwo = i;
                    break;
                case 3:
                    posOfThree = i;
                    break;
                case 4:
                    posOfFour = i;
                    break;
            }
        }

        chimpGame.makeMove(posOfOne);
        chimpGame.makeMove(posOfTwo);
        chimpGame.makeMove(posOfThree);
        chimpGame.makeMove(posOfFour);

        assertEquals(5, chimpGame.endGame());


    }

    @Test
    public void wrongAnswer(){

        int initLives = chimpGame.getLives();

        for (int i = 0; i < 40; i++){
            if (board[i] != 0 && board[i] != 1){
                chimpGame.makeMove(i);
                break;
            }
        }

        assertEquals(chimpGame.getLives(), initLives - 1);

    }

    @Test
    public void gameOverBlock(){
        boolean first = false;
        boolean second = false;

        chimpGame.endGame();


        try {
            chimpGame.isNumberVisible();
        } catch (RuntimeException e){
            if(e.getMessage().equals("Attempt to get number visibility after game over")){
                first = true;
            }
        }

        try {
            chimpGame.getBoard();
        } catch (RuntimeException e){
            if (e.getMessage().equals("Attempt to get board after game over")){
                second = true;
            }
        }

        assertTrue(first && second);
    }
}
