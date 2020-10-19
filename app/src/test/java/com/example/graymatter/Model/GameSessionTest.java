package com.example.graymatter.Model;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.social.GameSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

//until scoring impl, no assertions. Just check in db for correct results.
public class GameSessionTest {
    JsonTestHelper jsonTestHelper = new JsonTestHelper();
    String path = jsonTestHelper.getJsonString();
    GameSessionAccess gsa;
    GameSessionMapper gsm;



    @Before
    public void init(){
        gsa = new GameSessionAccess(path);
        gsm = new GameSessionMapper(path);
    }

   /* @Test
    public void storeGameTest(){
        gsa.storeGameSession(500, "ChimpGame");
        gsa.storeGameSession(670, "ShrimpGame");
        gsm.delete(gsm.find(gsa.getNewGameID()-1).get());
        gsm.delete(gsm.find(gsa.getNewGameID()-1).get());
    }
    */
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
