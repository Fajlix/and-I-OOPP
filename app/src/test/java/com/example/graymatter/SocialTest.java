package com.example.graymatter;

import android.media.Image;
import android.media.ImageReader;

import com.example.graymatter.Social.GameSession;
import com.example.graymatter.Social.Player;
import com.example.graymatter.Social.PlayerMapper;
import com.example.graymatter.Social.UserInfoException;
import com.example.graymatter.Social.dbModel;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SocialTest {
    PlayerMapper testPlayerMapper = new PlayerMapper();
    Player testUser;
    Player testPlayer;
    String path = "C:/Users/hanna/Documents/and-I-OOPP/app/src/main/java/com/example/graymatter/Social/testPlayers.json";
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
            testPlayerMapper.createNewAccountAndLogIn("johanna.j@gmail.com", "losen123", "jordgubben97");
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
        assertTrue(testPlayerMapper.isLoggedIn());
    }

    @Test
    public void logInTest(){

    }

    @Test
    public void privacyBreachTest(){

    }

    @Test
    public void findTest(){
        assertEquals(1, testPlayerMapper.find(1).get().getUserID());
    }

    @Test
    public void readGsonTest() throws FileNotFoundException {
        Gson gson = new Gson();
        dbModel nDb = gson.fromJson(new FileReader(path), dbModel.class);
        Player player = nDb.players.get(0);
        assertEquals(player.getUserID(), 1);
        assertEquals(player.getUserName(), "Mathilda97");
    }

    @Test
    public void writeGsonTest() throws FileNotFoundException {
        Gson gson = new Gson();
        testPlayerMapper = new PlayerMapper();
        try (FileWriter writer = new FileWriter("C:/Users/hanna/Documents/and-I-OOPP/app/src/main/java/com/example/graymatter/Social/testPlayers.json")) {
            dbModel dbM = gson.fromJson(new FileReader("C:/Users/hanna/Documents/and-I-OOPP/app/src/main/java/com/example/graymatter/Social/testPlayers.json"), dbModel.class);
            testPlayerMapper.logIn("Mathilda97", "losen123");
            gson.toJson(dbM, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbModel dbM = gson.fromJson(new FileReader("C:/Users/hanna/Documents/and-I-OOPP/app/src/main/java/com/example/graymatter/Social/testPlayers.json"), dbModel.class);

        assertEquals(dbM.players.get(0).getUserID(), 1);
    }
}
