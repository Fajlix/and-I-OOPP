package com.example.graymatter.ViewModel;


import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.dataAccess.DataAccess;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HanoiViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();
    public Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    TowerOfHanoiViewModel viewModel;

    @Before
    public void setUp(){
        viewModel = new TowerOfHanoiViewModel();
        viewModel.init(new DataAccess(context));
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
