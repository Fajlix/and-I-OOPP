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
     * Returns a leaderboard of results from a particular gameType, with only games played by the user marked as currentUser in local cache. Cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultsLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectFriendTopScores(int resultTop, int resultsLow, String gameType){
        //sort out games of nonmatching gametypes
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);
        //check for illegal resultspan
        resultsLow = legalResultSpan(resultTop, resultsLow, gameSessions.size());
        //below: normArrays[0] = gameIDs, normArrays[1] = normScores, normArrays[2] = ownerUserIDs
        //sort and norm the results
        int[][] normArrays = convertAndNorm(gameType);
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

    /**
     * Returns a leaderboard of results from a particular gameType, cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultsLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectGlobalTopScores(int resultTop, int resultsLow, String gameType){
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);
        resultsLow = legalResultSpan(resultTop, resultsLow, gameSessions.size());
        int[][] normArrays = convertAndNorm(gameType);
        int[][] normArraysWOwners = new int[3][];
        normArraysWOwners[0] = normArrays[0];
        normArraysWOwners[1] = normArrays[1];
        normArraysWOwners[2] = findGameOwners(normArrays[0]);
        return cutOutSelectedTopListPart(normArraysWOwners, resultTop, resultsLow, 3);
    }

    /**
     *
     * @param resultTop
     * @param resultsLow
     * @param resultsAmount
     * @param legalGameTypes A String of gameTypes, seperated by space " "
     * @return
     */
    public static int[][] getSelectGlobalTopPersonas(int resultTop, int resultsLow, int resultsAmount, String legalGameTypes){
        resultsLow = legalResultSpan(resultTop, resultsLow, resultsAmount);
        String[] gameTypes = legalGameTypes.split(" ");
        int[][] normArray = new int[2][];
        for (String gameType : gameTypes) {
            int[][] addArray = convertAndNorm(gameType);
            int sI = normArray.length;
            normArray[0] = Arrays.copyOf(normArray[0], addArray.length);
            normArray[1] = Arrays.copyOf(normArray[1], addArray.length);
            for (int i = sI; i < normArray[0].length; i++) {
                normArray[0][i] = addArray[0][i-sI];
                normArray[1][i] = addArray[1][i-sI];
            }
        }
        int[][] normArraysWOwners = new int[3][];
        normArraysWOwners[0] = normArray[0];
        normArraysWOwners[1] = normArray[1];
        normArraysWOwners[2] = findGameOwners(normArray[0]);
        return cutOutSelectedTopListPart(normArraysWOwners, resultTop, resultsLow, 3);

    }

    public static int[][] getSelectFriendTopPersonas(int resultTop, int resultsLow, int resultsAmount){
        return new int[0][0];
    }

    /**
     * Sorter capable of sorting numbers x,  0 =< x => 2000. Top Scores on high index, low scores on low index.
     */
    private static void sortGameSessionsByScore(List<GameSession> bfGS){
        for (int i = 0; i < bfGS.size(); i++){
            for (int o =i; o < bfGS.size(); o++){
                if(bfGS.get(i).getScore() > bfGS.get(o).getScore()){
                    GameSession temp = bfGS.get(i);
                    bfGS.set(i, bfGS.get(o));
                    bfGS.set(o, temp);
                }
            }
        }
    }

    /**
     *
     * @param gameType
     * @return
     */
    private static int[][] convertAndNorm(String gameType){
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);
        sortGameSessionsByScore(gameSessions);
        int[] scores = new int[gameSessions.size()];
        int[] gameID = new int[gameSessions.size()];
        for (int i = 0; i < gameSessions.size(); i++) {
            scores[i] = gameSessions.get(i).getScore();
            gameID[i] = gameSessions.get(i).getGameID();
        }
        int[][] normScores = NormScore.normScores(scores);
        //revert it TODO fix something smoother
        int[][] revertedNormScores = new int[2][normScores[0].length];
        for (int i = 0; i < normScores[0].length; i++) {
            revertedNormScores[0][i] = gameID[normScores[0].length-i-1];
            revertedNormScores[1][i] = normScores[1][normScores[0].length-i-1];
        }
        return revertedNormScores;
    }


    private static int[] findGameOwners(int[] gameIDs){
        int[] userIDs = new int[gameIDs.length];
        for (int i = 0; i < gameIDs.length; i++) {
            userIDs[i] = gsa.getGameOwnerUserID(gameIDs[i]);
        }
        return userIDs;
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
        int[][] justFriends = new int[notJustFriends.length][notJustFriends[0].length];
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

    //a bit dirty
    private static int legalResultSpan(int resultsTop, int resultsLow, int listlength) throws IllegalArgumentException{
        if(resultsLow < resultsTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        if(listlength < resultsLow){
            return listlength;
        }
        return resultsLow;
    }


}
