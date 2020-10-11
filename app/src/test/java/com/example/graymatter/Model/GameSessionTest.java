package com.example.graymatter.Model;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;

import org.junit.Before;
import org.junit.Test;

//until scoring impl, no assertions. Just check in db for correct results.
public class GameSessionTest {

    String path = "src/main/assets/testPlayers.json";
    GameSessionAccess gsa;


    @Before
    public void init(){
        gsa = new GameSessionAccess(path);
    }

    @Test
    public void storeGameTest(){
        gsa.storeGameSession(500, "ChimpGame");
        gsa.storeGameSession(670, "ShrimpGame");
    }

    @Test
    public void removeGameTest(){
    }

}
