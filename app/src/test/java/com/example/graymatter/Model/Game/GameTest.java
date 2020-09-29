package com.example.graymatter.Model.Game;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class GameTest {
    private boolean observerMethodWasCalled;
    GameObserver gameObserver = new GameObserver() {
        @Override
        public void update() {
            observerMethodWasCalled = true;
        }
    };
    @Before
    public void beforeTests(){
    }


    @Test
    public void removeObserver() {
        observerMethodWasCalled = false;
        Game game = new Game();
        game.addObserver(gameObserver);
        game.removeObserver(gameObserver);
        game.notifyObservers();
        assertFalse(observerMethodWasCalled);
    }

    @Test
    public void notifyObservers() {
        Game game = new Game();
        observerMethodWasCalled = false;
        game.addObserver(gameObserver);
        game.notifyObservers();
        assertTrue(observerMethodWasCalled);
    }

    @Test
    public void startGame() {
        Game game = new Game();
        game.StartGame();
    }

    @Test
    public void stopGame() {
        Game game = new Game();
        game.StopGame();
    }

    @Test
    public void changeState() {
        Game game = new Game();
        GameState gameState1 = new GameState() {
            @Override
            public void StartGame() {

            }

            @Override
            public int StopGame() {
                return 0;
            }
        };
        GameState gameState2 = new GameState() {
            @Override
            public void StartGame() {

            }

            @Override
            public int StopGame() {
                return 0;
            }
        };
        game.ChangeState(gameState1);
        assertEquals(game.getGameState(), gameState1);
        game.ChangeState(gameState2);
        assertEquals(game.getGameState(),gameState2);
    }
}