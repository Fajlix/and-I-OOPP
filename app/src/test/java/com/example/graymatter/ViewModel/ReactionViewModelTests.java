package com.example.graymatter.ViewModel;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReactionViewModelTests {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    ReactionTimeViewModel viewModel;

    @Before
    public void setUp(){
        viewModel = new ReactionTimeViewModel();
        viewModel.init();
    }

    @Test
    public void test() throws InterruptedException {
        viewModel.startReactionGame();

        Thread.sleep(4000);

        viewModel.endReactionTimeGame();

        int score = viewModel.getScore();

        assertTrue(score > 0);


    }


}
