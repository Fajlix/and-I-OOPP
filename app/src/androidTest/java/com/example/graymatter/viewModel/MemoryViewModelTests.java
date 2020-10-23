package com.example.graymatter.viewModel;



import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.model.game.memoryGame.MemoryGrid;
import com.example.graymatter.model.dataAccess.DataAccess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MemoryViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    public Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    MemoryGameViewModel viewModel;

    @Before
    public void setUp(){
        viewModel = new MemoryGameViewModel();
        viewModel.init(new DataAccess(context));
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
