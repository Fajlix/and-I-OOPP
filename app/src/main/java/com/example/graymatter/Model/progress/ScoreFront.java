package com.example.graymatter.Model.progress;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.PlayerAccess;
import com.example.graymatter.Model.dataAccess.social.GameSession;


import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class ScoreFront {

    private static GameSessionAccess gsa = new GameSessionAccess("src/main/assets/testPlayers.json"); //comes from somewhere?
    private static PlayerAccess pa = new PlayerAccess("src/main/assets/testPlayers.json"); //same

    public static void storeGameSession(int unNorm, String gameType){
        //int normScore = NormScore.normScore(unNorm, gameType);
       // gsa.storeGameSession(normScore, gameType);
    }

    /**
     *
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultsLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return A Map<Integer gameID, Integer score> where gameID represents
     *     where the userID key matches the player of the mapped GameSession. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboardd position.
     * Integer UserID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static Map<Integer, Integer> getSelectGlobalTopScores(int resultTop, int resultsLow, String gameType){
        if(resultsLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);                //TODO getGameSessionsByType should return Map<gameID, score>
        if(gameSessions.size() < resultsLow){
            resultsLow = gameSessions.size();
        }
        Map<Integer, Integer> selectGlobalTopScores = new HashMap<>();
        sortGameSessionsByScore(gameSessions);
        int[] scores = new int[gameSessions.size()];
        int[] userIDs = new int[gameSessions.size()];
        for (int i = 0; i < gameSessions.size(); i++) {
            scores[i] = gameSessions.get(i).getScore();
            userIDs[i] = pa.getGameIDOwner(gameSessions.get(i).getGameID());
        }
        int[] normScores = NormScore.normScores(scores);
        gameSessions.subList(resultTop, resultsLow);
        int[] desNormScores = Arrays.copyOfRange(normScores, resultTop, resultsLow);
        int[] desUserIDs = Arrays.copyOfRange(userIDs, resultTop, resultsLow);
        for (int i = 0; i < scores.length-1; i++) {
            selectGlobalTopScores.put(desUserIDs[i], desNormScores[i]);
        }
        return selectGlobalTopScores;
    }

    public static Map<Integer, GameSession> getSelectFriendTopScores(int resultTop, int resultsLow, String gameType){
        return new HashMap<>();
    }

    public static Map<Integer, Integer> getSelectGlobalTopPersonas(int resultTop, int resultsLow, int resultsAmount){
        return new HashMap<>();
    }

    public static Map<Integer, Integer> getSelectFriendTopPersonas(int resultTop, int resultsLow, int resultsAmount){
        return new HashMap<>();
    }

    /**
     * Sorter capable of sorting numbers x,  0 =< x => 2000. Top Scores on low index, low scores on high index.
     */
    private static void sortGameSessionsByScore(List<GameSession> bfGS){
        //TODO sorting algoritm
    }
}
