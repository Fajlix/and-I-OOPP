package com.example.graymatter.Model.progress;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.PlayerAccess;
import com.example.graymatter.Model.dataAccess.social.GameSession;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * Class contains methods returning different kind of leaderboards with normated scores.
 * Methods renormates score every time called. For bigger databases these calls are demanding. This class should be located at serverside and methods should be called sparsely.
 */
public class ScoreFront {

    private GameSessionAccess gsa; //comes from somewhere?
    PlayerAccess pa;

    public ScoreFront(GameSessionAccess gsa, PlayerAccess pa){//TODO näej tänk
        this.gsa = gsa;
        this.pa = pa;
    }

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a particular gameType, with only games played by the user marked as currentUser in local cache. Cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public int[][] getSelectFriendTopScores(int resultTop, int resultLow, String gameType) throws UserInfoException {
        List<Integer> friendIDs = pa.getCurrentPlayer().getFriendUserIDs();
        friendIDs.add(pa.getCurrentPlayer().getUserID());
        return NormLeaderboards.getSelectFriendTopScores(getGameData(gameType), resultTop, resultLow, friendIDs);
    }

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a particular gameType, cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public int[][] getSelectGlobalTopScores(int resultTop, int resultLow, String gameType){
        return NormLeaderboards.getSelectGlobalTopScores(getGameData(gameType), resultTop, resultLow);
    }


    /**
     * Returns a leaderboard of players and corresponding normated scores. Based on persona, a.k.a. overall topscoring player.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param legalGameTypes A String of gameTypes, seperated by space " ". Retained from TODO this should have dedicated method either in GameSessionAccess or some formal legal shell above
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public int[][] getSelectGlobalTopPersonas(int resultTop, int resultLow, String legalGameTypes){
        int[][] allData = getGlobalTopPersonas(legalGameTypes);
        return NormLeaderboards.getSelectGlobalTopPersonas(allData, resultTop, resultLow);

    }


    /**
     * Returns a leaderboard of players on current users friend list (current user according to cache) and corresponding normated scores. Based on persona, a.k.a. overall topscoring player.
     * Friends rankings does not indicate complete playerbase relative ranking, however scores are normated after entire playerbase.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param legalGameTypes A String of gameTypes, seperated by space " ".
     * @return
     * @throws UserInfoException
     */
    public int[][] getSelectFriendTopPersonas(int resultTop, int resultLow, String legalGameTypes) throws UserInfoException {
        int[][] allData = getGlobalTopPersonas(legalGameTypes);
        List<Integer> friendIDs = pa.getCurrentPlayer().getFriendUserIDs();
        friendIDs.add(pa.getCurrentPlayer().getUserID());
        return NormLeaderboards.getSelectFriendTopPersonas(allData, resultTop, resultLow, friendIDs);
    }

    /**
     *
     * @param legalGameTypes A String of gameTypes, seperated by space " ". Retained from
     * @return twocolumn matrix, int[0] containing userIDs, int[1] norm scores. [2] gameIDs
     */
    private int[][] getGlobalTopPersonas(String legalGameTypes){
        String[] gameTypes = legalGameTypes.split(" ");
        int[][] normArray = new int[gameTypes.length*3][];
        int newNAI = 0;
        for (String gameType : gameTypes) {
            //normArray[0] gameIDs, normArray[1] norm scores
            int[][] addArray = getGameData(gameType);
            normArray[newNAI] = addArray[0];
            normArray[newNAI+1] = addArray[1];
            normArray[newNAI+2] = addArray[2];
            newNAI += 3;
        }
        return normArray;
    }

    /**
     * Sorts list of GameSessions based on score, numbers x,  0 =< x => 2000. Top Scores on high index, low scores on low index.
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
     * @return int[0] containing gameIDs, int[1] containing normed scores
     */
    /*
    private static int[][] findAndNorm(String gameType){
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
*/

    private int[][] getGameData(String gameType){
        List<GameSession> gameSessions = gsa.getGameSessionsByType(gameType);
        int[][] gs = new int[3][gameSessions.size()];

        for (int i = 0; i < gameSessions.size(); i++) {
            gs[1][i] = gameSessions.get(i).getScore();
            gs[2][i] = gameSessions.get(i).getGameID();
        }
        gs[0] = findGameOwners(gs[2]);
        return gs;
    }
 /*
    private static int[][] revert(int[][] toRevert){
        int[][] revertedNormScores = new int[toRevert.length][toRevert[0].length];
        for (int i = 0; i < toRevert[0].length; i++) {
            for (int j = 0; j < toRevert.length; j++) {
                revertedNormScores[j][i] = toRevert[j][toRevert.length-i-1];
            }
        }
        return revertedNormScores;
    }
*/
    private int[] findGameOwners(int[] gameIDs){
        int[] userIDs = new int[gameIDs.length];
        for (int i = 0; i < gameIDs.length; i++) {
            userIDs[i] = gsa.getGameOwnerUserID(gameIDs[i]);
        }
        return userIDs;
    }
}
