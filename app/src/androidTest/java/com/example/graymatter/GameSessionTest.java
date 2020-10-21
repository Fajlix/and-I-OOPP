package com.example.graymatter;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.social.GameSession;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

//until scoring impl, no assertions. Just check in db for correct results.
@RunWith(AndroidJUnit4.class)
public class GameSessionTest {

    String path = "src/main/assets/testPlayers.json";
    DataAccess gsa;
    GameSessionMapper gsm;


    @Before
    public void init(){
        gsa = new DataAccess(InstrumentationRegistry.getInstrumentation().getTargetContext());
        gsm = new GameSessionMapper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        try {
            gsa.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storeGameTest(){
        gsa.storeGameSession(500, "ChimpGame");
        gsa.storeGameSession(670, "ShrimpGame");
        gsm.delete(gsm.find(gsa.getNewGameID()-1).get());
        gsm.delete(gsm.find(gsa.getNewGameID()-1).get());
    }

    @Test
    public void removeGameTest(){

    }

    @Test
    public void gameSessionAccessTest(){
        GameSession gs = gsa.getGameSession(34);
        Assert.assertEquals("ChimpGame", gs.getGameType());
    }

    @Test
    public void gameSessionGettersTest(){
        GameSession gs = new GameSession(125, 678, "MemoryGame", LocalDate.now());
        Assert.assertEquals(LocalDate.now(), gs.getDate());

    }
}
