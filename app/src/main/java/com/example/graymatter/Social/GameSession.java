package com.example.graymatter.Social;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Respresenting a game session, associated from a Player.
 */
public class GameSession {
    int gameID;
    int score;
    String gameType;
    String time;

    /**
     * Standard constructor used for documenting a game session.
     * @param gameID Unique GameId 1 and above supplied by databasemapper.
     * @param score Not below 0. Normated with Neurån scoring.
     * @param gameType String representing game type. Needs to formatted likewise for every game entry from same game.
     */
    public GameSession(int gameID, int score, String gameType){
        this.gameID = gameID;
        this.score = score;
        this.gameType = gameType;
       // this.time = LocalTime.now();
    }

    /**
     * For recreating old game sessions. Intended for database use.
     * @param gameID gameID from game that's to be recreated.
     * @param score score from game that's to be recreated.
     * @param gameType gameType from game that's to be recreated.
     * @param time time from game that's to be recreated.
     */
    public GameSession(int gameID, int score, String gameType, String time){
        this.gameID = gameID;
        this.score = score;
        this.gameType = gameType;
        this.time = time;
    }

    /**
     * Copy constructor
     * @param gameSession gameSessin to be copies
     */
    public GameSession(GameSession gameSession){
        this(gameSession.gameID, gameSession.score, gameSession.gameType, gameSession.time);
    }
}
