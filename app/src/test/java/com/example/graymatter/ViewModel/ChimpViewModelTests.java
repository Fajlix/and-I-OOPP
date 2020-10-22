package com.example.graymatter.ViewModel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChimpViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    ChimpGameViewModel viewModel;
    LiveData<int[]> grid;

    @Before
    public void init(){
        viewModel = new ChimpGameViewModel();
        viewModel.init();
        viewModel.startChimpGame();
        grid = viewModel.getGrid();
    }


    @Test
    public void test(){
        int first = 0;
        int second = 0;
        int third = 0;
        int fourth = 0;

        for (int i = 0; i<grid.getValue().length; i++){
            switch (grid.getValue()[i]){
                case 1:
                    first = i;
                    break;
                case 2:
                    second = i;
                    break;
                case 3:
                    third = i;
                    break;
                case 4:
                    fourth = i;
                    break;
                default:
                    break;
            }
        }

        viewModel.tileHasBeenClicked(first);
        viewModel.tileHasBeenClicked(second);
        viewModel.tileHasBeenClicked(third);
        viewModel.tileHasBeenClicked(fourth);
        for (int j = 0; j<3; j++) {
            for (int i = 0; i < grid.getValue().length; i++) {
                if (grid.getValue()[i] == 2) {
                    viewModel.tileHasBeenClicked(i);
                    break;
                }
            }
        }
        assertEquals(5, viewModel.getScore());
    }
}
