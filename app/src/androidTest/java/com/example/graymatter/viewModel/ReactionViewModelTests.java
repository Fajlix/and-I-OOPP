package com.example.graymatter.viewModel;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.model.dataAccess.DataAccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ReactionViewModelTests {
    public Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    ReactionTimeViewModel viewModel;

    @Before
    public void setUp(){
        viewModel = new ReactionTimeViewModel();
        viewModel.init(new DataAccess(context));
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
