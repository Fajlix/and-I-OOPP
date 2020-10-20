package com.example.graymatter;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.PlayerAccess;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.PlayerMapper;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;
import com.example.graymatter.Model.progress.NormScore;
import com.example.graymatter.Model.progress.ScoreFront;
import com.example.graymatter.Model.progress.Sort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProgressTest {
    String path = "src/main/assets/testPlayers.json";
    int[] scores;
    DataAccess gsa;
    PlayerAccess pa;
    TestContextHelper con = new TestContextHelper();
    @Before
    public void init(){
        scores = new int[]{8, 13, 5, 7, 1, 2, 16, 99, 2, 0, 13, 13, 13, 13, 13, 9, 9, 9, 5, 12};
        gsa = new DataAccess(path, InstrumentationRegistry.getInstrumentation().getTargetContext());
        pa = new PlayerAccess(path);
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
        gsa.storeGameSession(1500, "ChimpGame");
        gsa.storeGameSession(0, "ChimpGame");
        int[][] scores = ScoreFront.getSelectGlobalTopScores(1, 12, "ChimpGame");
        Assert.assertEquals(pa.getCurrentPlayer().getUserID(), scores[2][0]);
        Assert.assertEquals(gsa.getNewGameID()-2, scores[0][0]);
        Assert.assertEquals(pa.getCurrentPlayer().getUserID(), scores[2][scores[2].length-1]);
        Assert.assertEquals(gsa.getNewGameID()-1, scores[0][scores[0].length-1]);
        GameSessionMapper mapper = new GameSessionMapper(path);
        //if tests fail bf below db will have to be manually fixed
        int aDel = gsa.getNewGameID()-1;
        int bDel = gsa.getNewGameID()-2;
        mapper.delete(mapper.find(aDel).get());
        mapper.delete(mapper.find(bDel).get());
        PlayerMapper pMapper = new PlayerMapper(path);
        List<Integer> gs = pa.getCurrentPlayer().getPlayerHistory();
        gs.remove((Integer) aDel);
        gs.remove((Integer) bDel);
        pMapper.update(pa.getCurrentPlayer());
    }

    @Test
    public void friendsTopScoresTest() throws UserInfoException {
        int[][] scores = ScoreFront.getSelectFriendTopScores(1, 15, "ChimpGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i] + " " + scores[2][i]);
        }
    }

    @Test
    public void friendPersonaScoresTest() throws UserInfoException {
        int[][] scores = ScoreFront.getSelectFriendTopPersonas(1, 3, "ChimpGame MemoryGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i]);
        }
    }

    @Test
    public void globalPersonaScoresTest(){
        int[][] scores = ScoreFront.getSelectGlobalTopPersonas(1, 5, "ChimpGame MemoryGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i]);
        }
    }




}
