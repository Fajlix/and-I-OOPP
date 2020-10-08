package com.example.graymatter.Social;

import java.time.LocalDate;

/**
 * Respresenting a game session, associated from a Player.
 */
public class GameSession {


    private int score;
    private String gameType;
   // LocalTime time; interesting?
    private LocalDate date;
    private int gameID;



    /**
     * Standard constructor used for documenting a game session.
     * @param gameID Unique GameId 1 and above supplied by databasemapper.
     * @param score Not below 0. Normated with Neurån scoring.
     * @param gameType String representing game type. Needs to formatted likewise for every game entry from same game.
     */
    protected GameSession(int gameID, int score, String gameType, LocalDate date){
        /* should be in database
        this.date = LocalDate.parse(gameID.substring(0,gameID.indexOf("S")));
        this.gameID = gameID.substring(gameID.indexOf("S") + 1);
        */
        this.date = date;
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
    /*
    public GameSession(String gameID, int score, String gameType){
        this.gameID = gameID;
        this.score = score;
        this.gameType = gameType;
    }
*/
    /**
     * Copy constructor
     * @param gameSession gameSession to be copies
     */
    protected GameSession(GameSession gameSession){
        this(gameSession.gameID, gameSession.score, gameSession.gameType, gameSession.date);
    }

    public int getGameID() {
        return gameID;
    }

    public int getScore() {
        return score;
    }

    public String getGameType() {
        return gameType;
    }

    public LocalDate getDate() {
        return date;  //should deepcopy
    }
/*
    public String getTime() {
        return time;
    }
    */

}
