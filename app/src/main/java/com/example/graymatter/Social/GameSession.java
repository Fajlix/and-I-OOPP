package com.example.graymatter.Social;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameSession {
    int gameID;
    int score;
    String gameType;
    String time;

    public GameSession(int score, String gameType){
        this.score = score;
        this.gameType = gameType;
       // this.time = LocalTime.now();
    }

    /**
     * for recreating old game sessions
     * @param gameID
     * @param score
     * @param gameType
     * @param time
     */
    public GameSession(int gameID, int score, String gameType, String time){
        this.gameID = gameID;
        this.score = score;
        this.gameType = gameType;
        this.time = time;
    }
}
