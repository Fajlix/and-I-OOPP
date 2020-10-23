package com.example.graymatter.model.dataAccess.social;

/**
 * Representing a game session, a.k.a all history stored from one instance of a game being played.
 */
public class GameSession {


    private final int score;
    private final String gameType;
    private final String dateString;
    private final int gameID;



    /**
     * Standard constructor used for documenting a game session.
     * @param gameID Unique GameId 1 and above supplied by databasemapper.
     * @param score Not below 0. Unnormated. Bigger score is a better result than a lower. //TODO is this what we decided on?
     * @param gameType String representing game type. Needs to formatted likewise for every game entry from same game. //TODO we never decided on this?
     */
    public GameSession(int gameID, int score, String gameType, String dateString){
        this.dateString = dateString;
        this.gameID = gameID;
        this.score = score;
        this.gameType = gameType;
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
     *//*
    protected GameSession(GameSession gameSession){
        this(gameSession.gameID, gameSession.score, gameSession.gameType, gameSession.date);
    }
    */

    /**
     * @return the gameID identifying the GameSession instance.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * @return the score of the GameSession instance. Not below 0. Unnormated. Bigger score is a better result than a lower.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return String representing game type.
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * @return date of the GameSession. TimeZone according to OS of the device the GameSession was played on.
     */
    public String getDate() {
        return dateString;  //should deepcopy
    }

}
