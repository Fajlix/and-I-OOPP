package com.example.graymatter;

import android.media.Image;
import android.media.ImageReader;

import com.example.graymatter.Social.GameSession;
import com.example.graymatter.Social.Player;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class SocialTest {
    Player testPlayer;

    @Test
    public void constructorsPlayerTest(){
        ImageReader img = null;
        try {
            img = ImageIO.read(new File("strawberry.jpg"));
        } catch (IOException e) {
        }
        testPlayer = new Player(346, 3, "a@hej.com", "losen123", "Jordgubben97", Image testImage, new ArrayList<GameSession>(), new ArrayList<Integer>);
    }

    @Test
    public void registrationTest(){

    }

    @Test
    public void logInTest(){

    }

    @Test
    public void privacyBreachTest(){

    }


}
