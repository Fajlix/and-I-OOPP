package com.example.graymatter;

import com.example.graymatter.Social.Player;
import com.example.graymatter.Social.PlayerAccess;
import com.example.graymatter.Social.PlayerMapper;
import com.example.graymatter.Social.UserInfoException;
import com.example.graymatter.Social.DataBaseModel;
import com.google.gson.Gson;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class SocialTest {
    String path = "src/main/assets/testPlayers.json";
    PlayerAccess testPlayerAccess = new PlayerAccess(path);
    Player testUser;
    Player testPlayer;
/**

    public void constructorsPlayerTest(){
        ImageReader img = null;
        try {
            img = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        testPlayer = new Player(346, 3, "a@hej.com", "losen123", "Jordgubben97", Image testImage, new ArrayList<GameSession>(), new ArrayList<Integer>);
    }**/

/*
    @Test
    public void getPlayerTest(){

    }

    @Test
    public void privacyBreachTest(){

    }
    **/

    @Test
    public void deleteTest() throws UserInfoException {
        testPlayerAccess.logOut();
        testPlayerAccess.createNewAccountAndLogIn("shortlife@hotmail.se", "123Ninja_", "lana-del-rey-fan");
        assertTrue(testPlayerAccess.isLoggedIn());
        testPlayerAccess.deleteAccount("123Ninja_");
        assertFalse(testPlayerAccess.isLoggedIn());
    }

    @Test
    public void changesTest() throws UserInfoException {
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "losen358");
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
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "losen358");
        assertEquals(testPlayerAccess.getEmail(), "1337manneeeeen@gmail.com");
        assertEquals(testPlayerAccess.currentPlayer.getUserID(), 15);
        assertEquals(testPlayerAccess.currentPlayer.getUserName(), "Tuff-tuff22oHalvt");
    }

    @Test
    public void addAndRemoveFriend() throws UserInfoException, FileNotFoundException {
        testPlayerAccess.logIn("Tuff-tuff22oHalvt", "losen358");
        //test addition of friend
        testPlayerAccess.addFriend(1);
        assertTrue(testPlayerAccess.find(15).get().getFriendUserIDs().contains(1));
        assertTrue(testPlayerAccess.find(1).get().getFriendUserIDs().contains(15));
        //test removal of friend
        testPlayerAccess.removeFriend(1);
        assertFalse(testPlayerAccess.find(15).get().getFriendUserIDs().contains(1));
        assertFalse(testPlayerAccess.find(1).get().getFriendUserIDs().contains(15));

    }

    @Test
    public void findTest(){
        assertEquals(1, testPlayerAccess.find(1).get().getUserID());
    }

    @Test
    public void readGsonTest() throws FileNotFoundException {
        Gson gson = new Gson();
        DataBaseModel nDb = gson.fromJson(new FileReader(path), DataBaseModel.class);
        assertEquals((Integer) nDb.numbers.get("lastUserID"),(Integer) 15);
    }

    @Test
    public void logInAndOutTest() throws FileNotFoundException {
        testPlayerAccess.logIn("Mathilda97", "losen123");
        assertEquals(testPlayerAccess.currentPlayer.getUserID(), 1);
        testPlayerAccess.logOut();
    }
}
