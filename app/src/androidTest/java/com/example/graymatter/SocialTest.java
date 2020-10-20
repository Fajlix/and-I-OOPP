package com.example.graymatter;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.LocalDataMapper;
import com.example.graymatter.Model.dataAccess.dataMapperImplementation.PlayerMapper;
import com.example.graymatter.Model.dataAccess.social.Player;
import com.example.graymatter.Model.dataAccess.PlayerAccess;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Optional;

@RunWith(AndroidJUnit4.class)
public class SocialTest {
    String path = "src/main/assets/testPlayers.json";
    DataAccess testPlayerAccess = new DataAccess(path, InstrumentationRegistry.getInstrumentation().getTargetContext());
    LocalDataMapper ldm = new LocalDataMapper(InstrumentationRegistry.getInstrumentation().getTargetContext());


    @Before
    public void init() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void ldmTest(){
        System.out.println(ldm.getCurrentPlayerUserID());
        ldm.setCurrentPlayerUserID(1);
        System.out.println(ldm.getCurrentPlayerUserID());
        ldm.setCurrentPlayerUserID(15);
    }

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
        assertEquals(testPlayerAccess.getEmail(), "1337manneeeeen@gmail.com");
        assertEquals(testPlayerAccess.currentPlayer.get().getUserID(), 15);
        assertEquals(testPlayerAccess.currentPlayer.get().getUserName(), "Tuff-tuff22oHalvt");
    }

    @Test
    public void addAndRemoveFriend() throws UserInfoException {
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
    public void friendsVaryingSuccessTest() throws UserInfoException {
        testPlayerAccess.addFriend(4);
        Assert.assertTrue(testPlayerAccess.getFriends().contains(new PlayerMapper(path).find(4).get()));
        testPlayerAccess.removeFriend(4);
    }

    @Test
    public void friendFails() throws UserInfoException {
        try {
            testPlayerAccess.addFriend(1);
        } catch (DataMapperException e){}
        try {
            testPlayerAccess.addFriend(257);
        } catch (DataMapperException e){}
        testPlayerAccess.currentPlayer.get().addFriend(759);
        try {
            testPlayerAccess.removeFriend(759);
        } catch (DataMapperException e){}
        Assert.assertFalse(testPlayerAccess.currentPlayer.get().getFriendUserIDs().contains(759));
    }

    @Test
    public void findTest(){
        assertEquals(1, testPlayerAccess.playerMapper.find(1).get().getUserID());
    }

    @Test
    public void readGsonTest() {
        assertEquals(1, 15);
    }

    @Test
    public void logInAndOutTest() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.logIn("Mathilda97", "yihha123");
        assertEquals(1, testPlayerAccess.currentPlayer.get().getUserID());
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
    public void logInFails() throws UserInfoException {
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
    public void failedRegistrations() throws UserInfoException {
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
    public void repoLayerConstructorFails() throws UserInfoException {
        testPlayerAccess.logOut();
        PlayerAccess paF = new PlayerAccess(path);
        Assert.assertEquals(Optional.empty(), paF.currentPlayer);
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "hejNej88*");
    }

    @Test
    public void userInfoFails(){
        try {
            testPlayerAccess.currentPlayer.get().setPassword("felLosen-1337", "Yihaaa95!");
            Assert.fail();
        } catch (UserInfoException e){}
        try {
            testPlayerAccess.currentPlayer.get().setPassword("hejNej88*", "dåligt");
        } catch (UserInfoException e){}
        try {
            testPlayerAccess.currentPlayer.get().setEmail("felLosen", "dream-e-letter@gmail.com");
        } catch (UserInfoException e){}
    }

    @Test
    public void playerChanges(){
        testPlayerAccess.currentPlayer.get().setUserImage("userImgs/tjohoJag");
        Assert.assertEquals("userImgs/tjohoJag", testPlayerAccess.currentPlayer.get().getUserImage());
    }

}
