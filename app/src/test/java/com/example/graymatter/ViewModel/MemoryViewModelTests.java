package com.example.graymatter.ViewModel;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MemoryViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    MemoryGameViewModel viewModel;

    @Before
    public void setUp(){
        viewModel = new MemoryGameViewModel();
        viewModel.init();
        viewModel.startMemoryGame();
    }

    @Test
    public void test(){
        ArrayList<MemoryGrid.TileState> grid = viewModel.getGrid().getValue();

        for (int i = 0 ; i < grid.size() ; i++) {
            if (grid.get(i) == MemoryGrid.TileState.CORRECTHIDDEN){
                viewModel.tileHasBeenClicked(i);
            }
        }

        while (!viewModel.getGameOver().getValue()) {
            grid = viewModel.getGrid().getValue();
            int lives = viewModel.getLives();
            int i = 0;
            while (viewModel.getLives() == lives && !viewModel.getGameOver().getValue()) {
                if (grid.get(i) == MemoryGrid.TileState.INCORRECTHIDDEN) {
                    viewModel.tileHasBeenClicked(i);
                }
                i++;
            }
        }

        int level = viewModel.getLevel();

        assertTrue(level == 2);
    }

}
