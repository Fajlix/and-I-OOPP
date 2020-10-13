package com.example.graymatter.Model.progress;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.PlayerAccess;
import com.example.graymatter.Model.dataAccess.social.GameSession;


import java.util.ArrayList;
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
    public static int[][] getSelectGlobalTopScores(int resultTop, int resultsLow, String gameType){
        if(resultsLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);
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

    /**
     *
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultsLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs
     */
    public static int[][] getSelectFriendTopScores(int resultTop, int resultsLow, String gameType){
        //check for illegal resultspan
        if(resultsLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        //sort out games of nonmatching gametypes
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);
        if(gameSessions.size() < resultsLow){
            resultsLow = gameSessions.size();
        }
        //below: normArrays[0] = gameIDs, normArrays[1] = normScores, normArrays[2] = ownerUserIDs
        //norm the results
        int[][] normArrays = normProcess(gameType);
        //find owners of gameIDs
        int[][] normArraysWOwners = new int[3][];
        //first two columns are copied from normArrays
        normArraysWOwners[0] = normArrays[0];
        normArraysWOwners[1] = normArrays[1];
        normArraysWOwners[2] = findGameOwners(normArrays[0]);
        //filter out non-friends
        int[][] friendTopScores = filterFriends(normArraysWOwners);
        //cut out the unwanted rankings and return
        return cutOutSelectedTopListPart(friendTopScores, resultTop, resultsLow, 3);
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

    private static int[][] normProcess(String gameType){
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);                //TODO getGameSessionsByType should return Map<gameID, score>
        Map<Integer, Integer> selectGlobalTopScores = new HashMap<>();
        sortGameSessionsByScore(gameSessions);
        int[] scores = new int[gameSessions.size()];
        int[] gameID = new int[gameSessions.size()];
        for (int i = 0; i < gameSessions.size(); i++) {
            scores[i] = gameSessions.get(i).getScore();
            gameID[i] = gameSessions.get(i).getGameID();
        }
        int[] normScores = NormScore.normScores(scores);

        int[][] toReturn = new int[2][scores.length];
        toReturn[0] = gameID;
        toReturn[1] = normScores;
        return toReturn;
    }

    private static int[] findGameOwners(int[] gameIDs){

    }

    /**
     *
     * @param notJustFriends 3-column int matrix where notJustFriends[0] =
     * @return
     */
    private static int[][] filterFriends(int[][] notJustFriends){
        //start with Map because array needs determined length
        /*Map<Integer, Integer> friends = new HashMap<>();
        for (int i = 0; i < notJustFriends[0].length; i++) {
            if(pa.currentPlayer.getFriendUserIDs().contains(notJustFriends[0][i])){
                friends.put(notJustFriends[1][i], notJustFriends[1][i]);
            }
        }*/
        int[][] justFriends = new int[notJustFriends.length][notJustFriends.length];
        for (int i = 0; i < notJustFriends[0].length; i++) {
            if(pa.currentPlayer.getFriendUserIDs().contains(notJustFriends[0][i])){
                justFriends[0][i] = notJustFriends[0][i];
                justFriends[1][i] = notJustFriends[1][i];
                justFriends[2][i] = notJustFriends[2][i];
            }
        }
        //convert to array
        /*int[][] friendsArray = new int[2][friends.size()];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : friends.entrySet()) {
            friendsArray[0][i] = entry.getKey();
            friendsArray[1][i] = entry.getValue();
            i++;
        }
        return friendsArray;*/
        return justFriends;
    }

    private static int[][] cutOutSelectedTopListPart(int[][] unCut, int resultTop, int resultsLow, int colUnCut){
        int[][] cut = new int[colUnCut][1+resultsLow-resultTop];
        //the newIndex where we in friendSelectedTopScores place what's on oldIndex in friendTopScores
        int newI = 0;
        for (int oldI = resultTop-1; oldI < resultsLow; oldI++) {
            for (int col = 0; col < colUnCut; col++) {
                cut[col][newI] = unCut[col][oldI];
            }
            newI++;
        }
        return cut;
    }


}
