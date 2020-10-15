package com.example.graymatter.Model;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.social.GameSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//until scoring impl, no assertions. Just check in db for correct results.
public class GameSessionTest {

    String path = "src/main/assets/testPlayers.json";
    GameSessionAccess gsa;
    GameSessionMapper gsm;


    @Before
    public void init(){
        gsa = new GameSessionAccess(path);
        gsm = new GameSessionMapper(path);
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

}
