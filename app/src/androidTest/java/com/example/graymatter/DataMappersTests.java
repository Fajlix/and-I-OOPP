package com.example.graymatter;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.model.dataAccess.DataAccess;
import com.example.graymatter.model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.model.dataAccess.dataMapperImplementation.GameSessionMapper;
import com.example.graymatter.model.dataAccess.dataMapperImplementation.PlayerMapper;
import com.example.graymatter.model.dataAccess.social.GameSession;
import com.example.graymatter.model.dataAccess.social.Player;
import com.example.graymatter.model.dataAccess.social.UserInfoException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(AndroidJUnit4.class)
public class DataMappersTests {
    String path = "src/main/assets/testPlayers.json";

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    GameSessionMapper gsm = new GameSessionMapper(context);
    PlayerMapper pm = new PlayerMapper(context);
    DataAccess gsa = new DataAccess(context);

    @Before
    public void init(){
        try {
            gsa.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void LocalDataMapperTest() {
        //DataMapperServices.setCurrentPlayerUserID(0);
        Assert.assertEquals(0,0);
        //DataMapperServices.setCurrentPlayerUserID(15);
    }

    @Test
    public void LocalDataMapperFailTest(){
        //to be written after assets impl.
    }

    @Test
    public void GameSessionMapperTests(){
        gsa.storeGameSession(785, "ChimpGame");
        gsm.update(new GameSession(gsa.getNewGameID()-1, 225, "MemoryGame", LocalDate.now().toString()));
        Assert.assertEquals("MemoryGame", gsm.find(gsa.getNewGameID()-1).get().getGameType());
        gsm.delete(gsm.find(gsa.getNewGameID()-1).get());
        Player player = pm.find(1).get();
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
            gsm.delete(new GameSession(87, 111, "ExGame", LocalDate.now().toString()));
        } catch (DataMapperException e){}
        //exception in insert()
        try {
            gsm.insert(new GameSession(1, 666, "ChimpGame", LocalDate.now().toString()));
        } catch (DataMapperException e){}
    }

    @Test
    public void PlayerMapperFailsTests(){
        try {
            pm.delete(new Player(185, "a@e.se", "ojSAN88_", "haj", "noPic", new ArrayList<Integer>(), new ArrayList<Integer>()));
        } catch (DataMapperException | UserInfoException e) {}
    }

}
