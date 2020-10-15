package com.example.graymatter.Model;

import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.LocalDataMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.PlayerMapper;
import com.example.graymatter.Model.dataAccess.social.Player;
import com.example.graymatter.Model.dataAccess.PlayerAccess;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.DataBaseModel;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;


public class SocialTest {
    String path = "src/main/assets/testPlayers.json";
    PlayerAccess testPlayerAccess = new PlayerAccess(path);

    @Test
    public void deleteTest() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.createNewAccountAndLogIn("shortlife@hotmail.se", "123Ninja_", "lana-del-rey-fan");
        assertTrue(testPlayerAccess.isLoggedIn());
        testPlayerAccess.deleteAccount("123Ninja_");
        assertFalse(testPlayerAccess.isLoggedIn());
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void changesTest() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        testPlayerAccess.changeEmail("hejNej88*", "1337pojken@gmail.com");
        assertEquals(testPlayerAccess.getEmail(), "1337pojken@gmail.com");
        testPlayerAccess.changePassword("hejNej88*", "loseN-458");
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "loseN-458");
        assertTrue(testPlayerAccess.isLoggedIn());
        testPlayerAccess.changeEmail("loseN-458", "1337manneeeeen@gmail.com");
        testPlayerAccess.changePassword("loseN-458", "hejNej88*");
    }

    @Test
    public void gettersTest() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        assertEquals(testPlayerAccess.getEmail(), "1337manneeeeen@gmail.com");
        assertEquals(testPlayerAccess.currentPlayer.getUserID(), 15);
        assertEquals(testPlayerAccess.currentPlayer.getUserName(), "Tuff-tuff22oHalvt");
    }

    @Test
    public void addAndRemoveFriend() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        //test addition of friend
        testPlayerAccess.addFriend(4);
        assertTrue(testPlayerAccess.playerMapper.find(15).get().getFriendUserIDs().contains(4));
        assertTrue(testPlayerAccess.playerMapper.find(4).get().getFriendUserIDs().contains(15));
        //test removal of friend
        testPlayerAccess.removeFriend(4);
        assertFalse(testPlayerAccess.playerMapper.find(15).get().getFriendUserIDs().contains(4));
        assertFalse(testPlayerAccess.playerMapper.find(4).get().getFriendUserIDs().contains(15));
    }

    @Test
    public void friendsVaryingSuccessTest(){
        testPlayerAccess.currentPlayer.addFriend(12);
        Assert.assertTrue(testPlayerAccess.getFriends().contains(new PlayerMapper(path).find(1).get()));
    }

    @Test
    public void friendFails(){
        try {
            testPlayerAccess.addFriend(1);
        } catch (DataMapperException e){}
        try {
            testPlayerAccess.addFriend(257);
        } catch (DataMapperException e){}
        testPlayerAccess.currentPlayer.addFriend(759);
        try {
            testPlayerAccess.removeFriend(759);
        } catch (DataMapperException e){}
        Assert.assertFalse(testPlayerAccess.currentPlayer.getFriendUserIDs().contains(759));
    }

    @Test
    public void findTest(){
        assertEquals(1, testPlayerAccess.playerMapper.find(1).get().getUserID());
    }

    @Test
    public void readGsonTest() {
        assertEquals(LocalDataMapper.getCurrentPlayerUserID(), 15);
    }

    @Test
    public void logInAndOutTest() {
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Mathilda97", "yihha123");
        assertEquals(1, testPlayerAccess.currentPlayer.getUserID());
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void deleteAccountTest() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.createNewAccountAndLogIn("tjenare@hotmail.se", "saftHallon29?", "Lejonkungen");
        testPlayerAccess.deleteAccount("saftHallon29?");
        Assert.assertFalse (testPlayerAccess.isLoggedIn());
        Assert.assertFalse(testPlayerAccess.findByEmail("tjenare@hotmail.se").isPresent());
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void findersTest(){
        Assert.assertEquals(1, testPlayerAccess.getGameIDOwner(39));
        Assert.assertEquals(1, testPlayerAccess.findByEmail("mat@gmail.com").get().getUserID());
    }

    @Test
    public void userImageTest(){
        testPlayerAccess.changeUserImage("assets/pic");
    }

    @Test
    public void logInFails(){
        testPlayerAccess.logOut();
        try{
            testPlayerAccess.logIn("soffan25", "hejnej");
            Assert.fail();
        } catch (DataMapperException e){}  //Catching the exception is a success
        try {
            testPlayerAccess.logIn("Tuff-tuff22oHalvt", "harGlömt");
        } catch (DataMapperException e){}
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void failedRegistrations(){
        testPlayerAccess.logOut();
        try {
            testPlayerAccess.createNewAccountAndLogIn("1337manneeeeen@gmail.com", "robbansAcc-95", "klöverKung");
        } catch (DataMapperException | UserInfoException e){}
        try {
            testPlayerAccess.createNewAccountAndLogIn("katten@jansson.se", "tjena", "Broderna");
        } catch (DataMapperException | UserInfoException e){}
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void failedFinders(){
        Optional<Player> OPlayer1 = testPlayerAccess.findByUserName("mystiskaCheyen");
        if (OPlayer1.isPresent()){
            Assert.fail();
        }
        Optional<Player> OPlayer2 = testPlayerAccess.findByEmail("jansson@katten.com");
        if (OPlayer2.isPresent()){
            Assert.fail();
        }
    }

    @Test
    public void repoLayerConstructorFails(){
        testPlayerAccess.logOut();
        PlayerAccess paF = new PlayerAccess(path);
        Assert.assertNull(paF.currentPlayer);
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }
}
