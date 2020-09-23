package com.example.graymatter.Game;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private boolean observerMethodWasCalled;
    GameObserver gameObserver = new GameObserver() {
        @Override
        public void update() {
            observerMethodWasCalled = true;
        }
    };
    @Before
    public void beforeTests(){
        game = Game.getInstance();
        observerMethodWasCalled = false;
        Game.getInstance().addObserver(gameObserver);
    }

    @Test
    public void getInstance() {
        Game game2 = Game.getInstance();
        assertEquals(game,game2);
    }


    @Test
    public void removeObserver() {
        game.removeObserver(gameObserver);
    }

    @Test
    public void notifyObservers() {
        Game.getInstance().notifyObservers();
        assertTrue(observerMethodWasCalled);
    }

    @Test
    public void startGame() {
        game.StartGame();
    }

    @Test
    public void stopGame() {
        game.StopGame();
    }

    @Test
    public void changeState() {
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