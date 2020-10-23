package com.example.graymatter;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.Model.dataAccess.DataAccess;
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
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    ScoreFront sf = new ScoreFront(new DataAccess(context));

    int[] scores;
    DataAccess gsa;

    @Before
    public void init(){
        scores = new int[]{8, 13, 5, 7, 1, 2, 16, 99, 2, 0, 13, 13, 13, 13, 13, 9, 9, 9, 5, 12};
        gsa = new DataAccess(context);
        try {
            gsa.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
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
        int[][] matrix = new int[2][];
        matrix[0] = scores;
        matrix[1] = new int[scores.length];
        int[][] sortScores = Sort.multRowSort(matrix, true, 0);
        int[] sortedScores = Sort.sort(scores);
        int[][] normScores = NormScore.normScores(sortScores[0]);
        int[] normScoresComp = new int[]{0, 50, 150, 150, 250, 250, 300, 350, 475, 475, 475, 550, 750, 750, 750, 750, 750, 750, 900, 950};
        int[] normScoresCompR = new int[]{950, 900, 750, 750, 750, 750, 750, 750, 550, 475, 475, 475, 350, 300, 250, 250, 150, 150, 50, 0};
        Assert.assertEquals(sortedScores.length, normScores[0].length);
        for (int i = 0; i < sortedScores.length; i++) {
            Log.i("R: ", "" + normScoresCompR[i]);
            Log.i("N: ",  "" + normScores[1][i]);
            //Assert.assertEquals(normScoresCompR[i], normScores[1][i]);
        }
    }

    /*
    @Test
    public void globalTopScoresTest(){
        gsa.storeGameSession(1500, "ChimpGame");
        gsa.storeGameSession(0, "ChimpGame");
        int[][] scores = sf.getSelectGlobalTopScores(1, 12, "ChimpGame");
        Assert.assertEquals(gsa.getCurrentPlayer().getUserID(), scores[2][0]);
        Assert.assertEquals(gsa.getNewGameID()-2, scores[0][0]);
        Assert.assertEquals(gsa.getCurrentPlayer().getUserID(), scores[2][scores[2].length-1]);
        Assert.assertEquals(gsa.getNewGameID()-1, scores[0][scores[0].length-1]);
        GameSessionMapper mapper = new GameSessionMapper(context);
        //if tests fail bf below db will have to be manually fixed
        int aDel = gsa.getNewGameID()-1;
        int bDel = gsa.getNewGameID()-2;
        mapper.delete(mapper.find(aDel).get());
        mapper.delete(mapper.find(bDel).get());
        PlayerMapper pMapper = new PlayerMapper(context);
        List<Integer> gs = gsa.getCurrentPlayer().getPlayerHistory();
        gs.remove((Integer) aDel);
        gs.remove((Integer) bDel);
        pMapper.update(gsa.getCurrentPlayer());
    }

    @Test
    public void friendsTopScoresTest() throws UserInfoException {
        int[][] scores = sf.getSelectFriendTopScores(1, 15, "ChimpGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i] + " " + scores[2][i]);
        }
    }

    @Test
    public void friendPersonaScoresTest() throws UserInfoException {
        int[][] scores = sf.getSelectFriendTopPersonas(1, 3, "ChimpGame MemoryGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i]);
        }
    }

    @Test
    public void globalPersonaScoresTest(){
        int[][] scores = sf.getSelectGlobalTopPersonas(1, 5, "ChimpGame MemoryGame");
        for (int i = 0; i < scores[0].length; i++) {
            System.out.println(scores[0][i] + " " + scores[1][i]);
        }
    }

*/


}
