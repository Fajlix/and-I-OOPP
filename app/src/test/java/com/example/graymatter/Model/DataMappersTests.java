package com.example.graymatter.Model;

import com.example.graymatter.Model.Game.Game;
import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.LocalDataMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.PlayerMapper;
import com.example.graymatter.Model.dataAccess.social.GameSession;
import com.example.graymatter.Model.dataAccess.social.Player;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class DataMappersTests {
    String path = "src/main/assets/testPlayers.json";
    GameSessionMapper gsm = new GameSessionMapper(path);
    PlayerMapper pm = new PlayerMapper(path);


    @Test
    public void LocalDataMapperTest() {
        LocalDataMapper.setCurrentPlayerUserID(0);
        Assert.assertEquals(0,LocalDataMapper.getCurrentPlayerUserID());
        LocalDataMapper.setCurrentPlayerUserID(15);
    }

    @Test
    public void LocalDataMapperFailTest(){
        //to be written after assets impl.
    }

    @Test
    public void GameSessionMapperTests(){
        GameSessionAccess gsa = new GameSessionAccess(path);
        gsa.storeGameSession(785, "ChimpGame");
        gsm.update(new GameSession(gsa.getNewGameID()-1, 225, "MemoryGame", LocalDate.now()));
        Assert.assertEquals("MemoryGame", gsm.find(gsa.getNewGameID()-1).get().getGameType());
        gsm.delete(gsm.find(gsa.getNewGameID()-1).get());
        Player player = pm.find(LocalDataMapper.getCurrentPlayerUserID()).get();
        //TODO remove dumb gameID from playerHistory
        pm.update(player);
    }

    @Test
    public void GameSessionMapperFailsTest(){
        //exception in find
        Optional<GameSession> ghostGS = gsm.find(673);
        Assert.assertFalse(ghostGS.isPresent());
        //exception in delete
        try{
            gsm.delete(new GameSession(87, 111, "ExGame", LocalDate.now()));
        } catch (DataMapperException e){}
        //exception in insert()
        try {
            gsm.insert(new GameSession(1, 666, "ChimpGame", LocalDate.now()));
        } catch (DataMapperException e){}
    }

    @Test
    public void PlayerMapperFailsTests(){
        try {
            pm.delete(new Player(185, "a@e.se", "ojSAN88_", "haj", "noPic", new ArrayList<Integer>(), new ArrayList<Integer>()));
        } catch (DataMapperException | UserInfoException e) {}
    }

}
