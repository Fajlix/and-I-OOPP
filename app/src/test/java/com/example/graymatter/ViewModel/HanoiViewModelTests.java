package com.example.graymatter.ViewModel;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class HanoiViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    TowerOfHanoiViewModel viewModel;

    @Before
    public void setUp(){
        viewModel = new TowerOfHanoiViewModel();
        viewModel.init();
        viewModel.setLevel(3);
        viewModel.startToHGame();
    }


    @Test
    public void test(){

        viewModel.tileHasBeenClicked(HanoiRodPosition.LEFT, HanoiRodPosition.RIGHT);
        viewModel.tileHasBeenClicked(HanoiRodPosition.LEFT, HanoiRodPosition.MIDDLE);
        viewModel.tileHasBeenClicked(HanoiRodPosition.RIGHT, HanoiRodPosition.MIDDLE);
        viewModel.tileHasBeenClicked(HanoiRodPosition.LEFT, HanoiRodPosition.RIGHT);
        viewModel.tileHasBeenClicked(HanoiRodPosition.MIDDLE, HanoiRodPosition.LEFT);
        viewModel.tileHasBeenClicked(HanoiRodPosition.MIDDLE, HanoiRodPosition.RIGHT);
        viewModel.tileHasBeenClicked(HanoiRodPosition.LEFT, HanoiRodPosition.RIGHT);

        assertTrue(viewModel.getGameOver().getValue());

    }
}
