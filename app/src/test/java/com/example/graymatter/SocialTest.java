package com.example.graymatter;

import com.example.graymatter.Social.Player;
import com.example.graymatter.Social.PlayerMapper;
import com.example.graymatter.Social.UserInfoException;
import com.example.graymatter.Social.DataBaseModel;
import com.google.gson.Gson;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class SocialTest {
    PlayerMapper testPlayerMapper = new PlayerMapper();
    Player testUser;
    Player testPlayer;
    String path = "src/main/assets/testPlayers.json";
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

    @Test
    public void registrationTest(){
        try {
            testPlayerMapper.createNewAccountAndLogIn("johanna.j@gmail.com", "Hacker_2", "jordgubben97");
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
        assertTrue(testPlayerMapper.isLoggedIn());
    }

    @Test
    public void getPlayerTest(){

    }

    @Test
    public void privacyBreachTest(){

    }

    @Test
    public void deleteTest() throws UserInfoException {
        testPlayerMapper.logOut();
        testPlayerMapper.createNewAccountAndLogIn("shortlife@hotmail.se", "123Ninja_", "lana-del-rey-fan");
        testPlayerMapper.deleteAccount("123Ninja_");

    }

    @Test
    public void changesTest() throws UserInfoException {
        testPlayerMapper = new PlayerMapper();
        testPlayerMapper.logIn("Tuff-tuff22oHalvt", "losen358");
        testPlayerMapper.changeEmail("hejNej88*", "1337pojken@gmail.com");
        assertEquals(testPlayerMapper.getEmail(), "1337pojken@gmail.com");
        testPlayerMapper.changePassword("hejNej88*", "loseN-458");
        testPlayerMapper.logOut();
        testPlayerMapper.logIn("Tuff-tuff22oHalvt", "loseN-458");
        assertTrue(testPlayerMapper.isLoggedIn());
        testPlayerMapper.changeEmail("loseN-458", "1337manneeeeen@gmail.com");
        testPlayerMapper.changePassword("loseN-458", "hejNej88*");
    }

    @Test
    public void gettersTest() throws UserInfoException {
        testPlayerMapper = new PlayerMapper();
        testPlayerMapper.logIn("Tuff-tuff22oHalvt", "losen358");
        assertEquals(testPlayerMapper.getEmail(), "1337manneeeeen@gmail.com");
        assertEquals(testPlayerMapper.currentPlayer.getUserID(), 15);
        assertEquals(testPlayerMapper.currentPlayer.getUserName(), "Tuff-tuff22oHalvt");
    }

    @Test
    public void addAndRemoveFriend() throws UserInfoException, FileNotFoundException {
        testPlayerMapper.logIn("Tuff-tuff22oHalvt", "losen358");
        //test addition of friend
        testPlayerMapper.addFriend(1);
        assertTrue(testPlayerMapper.find(15).get().getFriendUserIDs().contains(1));
        assertTrue(testPlayerMapper.find(1).get().getFriendUserIDs().contains(15));
        //test removal of friend
        testPlayerMapper.removeFriend(1);
        assertFalse(testPlayerMapper.find(15).get().getFriendUserIDs().contains(1));
        assertFalse(testPlayerMapper.find(1).get().getFriendUserIDs().contains(15));

    }

    @Test
    public void findTest(){
        assertEquals(1, testPlayerMapper.find(1).get().getUserID());
    }

    @Test
    public void readGsonTest() throws FileNotFoundException {
        Gson gson = new Gson();
        DataBaseModel nDb = gson.fromJson(new FileReader(path), DataBaseModel.class);
        assertEquals((Integer) nDb.numbers.get("lastUserID"),(Integer) 15);
    }

    @Test
    public void logInAndOutTest() throws FileNotFoundException {
        Gson gson = new Gson();
        testPlayerMapper = new PlayerMapper();
        //va? varför?
        testPlayerMapper.logIn("Mathilda97", "losen123");
        assertEquals(testPlayerMapper.currentPlayer.getUserID(), 1);
        testPlayerMapper.logOut();
    }
}
