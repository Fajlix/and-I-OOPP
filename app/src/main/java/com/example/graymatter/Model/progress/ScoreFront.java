package com.example.graymatter.Model.progress;

import com.example.graymatter.Social.GameSession;
import com.example.graymatter.Social.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreFront {

    private static GameSessionAccess gsa; //comes from somewhere?
    private static PlayerAccess pa; //same

    public static void storeGameSession(int unNorm, String gameType){
        int normScore = Score.normScore(unNorm, gameType);
        gsa.storeGameSession(normScore, gameType);
    }

    /**
     *
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultsLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return A Map<Integer UserID, GameSession> where the userID key matches the player of the mapped GameSession. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboardd position.
     * Integer UserID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static Map<Integer, GameSession> getSelectGlobalTopScores(int resultTop, int resultsLow, String gameType){
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);                //TODO everything in GameSession needs to be protected. think it is
        sortGameSessionsByScore(gameSessions);
        gameSessions.subList(resultTop, resultsLow);
        Map<Integer, GameSession> selectGlobalTopScores = new HashMap<>();
        for (GameSession g: gameSessions){
            int userID = pa.getGameIDOwner(g.getGameID());
            selectGlobalTopScores.put(userID, g);
        }
        return selectGlobalTopScores;
    }

    public static Map<Integer, GameSession> getSelectFriendTopScores(int resultTop, int resultsLow, String gameType){

    }

    public static Map<Integer, Integer> getSelectGlobalTopPersonas(int resultTop, int resultsLow, int resultsAmount){

    }

    public static Map<Integer, Integer> getSelectFriendTopPersonas(int resultTop, int resultsLow, int resultsAmount){

    }

    /**
     * Sorter capable of sorting numbers x,  0 =< x => 2000. Top Scores on low index, low scores on high index.
     */
    private static void sortGameSessionsByScore(List<GameSession> bfGS){
        //TODO sorting algoritm
    }
}
