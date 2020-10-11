package com.example.graymatter.Model;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Model.progress.NormScore;
import com.example.graymatter.Model.progress.ScoreFront;
import com.example.graymatter.Model.progress.Sort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class ProgressTest {
    int[] scores;
    @Before
    public void init(){
        scores = new int[]{8, 13, 5, 7, 1, 2, 16, 99, 2, 0, 13, 13, 13, 13, 13, 9, 9, 9, 5, 12};
    }

    @Test
    public void sortTest(){
        int[] sortScores = Sort.sort(scores);
        int[] sortComp = new int[]{0, 1, 2, 2, 5, 5, 7, 8, 9, 9, 9, 12, 13, 13, 13, 13, 13, 13, 16, 99};
        Assert.assertEquals(sortScores.length, sortComp.length);
        for (int i = 0; i < sortScores.length; i++) {
            //System.out.print(sortScores[i]);
            //System.out.println(" " + sortComp[i]);
            Assert.assertEquals(sortScores[i], sortComp[i]);
        }
    }

    @Test
    public void normScoresTest(){
        int[] sortedScores = Sort.sort(scores);
        int[] normScores = NormScore.normScores(sortedScores);
        int[] normScoresComp = new int[]{0, 50, 150, 150, 250, 250, 300, 350, 475, 475, 475, 550, 750, 750, 750, 750, 750, 750, 900, 950};
        Assert.assertEquals(sortedScores.length, normScores.length);
        for (int i = 0; i < sortedScores.length; i++) {
            //System.out.print(sortedScores[i]);
            //System.out.println(" " + normScores[i]);
            Assert.assertEquals(normScores[i], normScoresComp[i]);
        }
    }

    @Test
    public void globalTopScoresTest(){
        Map<Integer, Integer> scoreMap = ScoreFront.getSelectGlobalTopScores(1, 5, "ChimpGame");
        for (Map.Entry<Integer, Integer> entry : scoreMap.entrySet()) {
            System.out.print(entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}
