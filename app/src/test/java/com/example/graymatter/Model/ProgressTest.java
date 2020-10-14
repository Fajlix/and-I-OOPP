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
        int[][] normScores = NormScore.normScores(sortedScores);
        int[] normScoresComp = new int[]{0, 50, 150, 150, 250, 250, 300, 350, 475, 475, 475, 550, 750, 750, 750, 750, 750, 750, 900, 950};
        Assert.assertEquals(sortedScores.length, normScores[0].length);
        for (int i = 0; i < sortedScores.length; i++) {
            //System.out.print(sortedScores[i]);
            //System.out.println(" " + normScores[i]);
            Assert.assertEquals(normScores[1][i], normScoresComp[i]);
        }
    }

    @Test
    public void globalTopScoresTest(){
        int[][] scores = ScoreFront.getSelectGlobalTopScores(1, 7, "ChimpGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i] + " " + scores[2][i]);
        }
    }

    @Test
    public void friendsTopScoresTest(){
        int[][] scores = ScoreFront.getSelectFriendTopScores(1, 15, "ChimpGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i] + " " + scores[2][i]);
        }
    }

    @Test
    public void friendPersonaScoresTest(){
        int i = (int) 99.9;
        System.out.println(i);

    }


}
