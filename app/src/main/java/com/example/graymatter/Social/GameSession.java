package com.example.graymatter.Social;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameSession {
    int score;
    String gameType;
    LocalTime time;

    public GameSession(int score, String gameType){
        this.score = score;
        this.gameType = gameType;
       // this.time = LocalTime.now();
    }
}
