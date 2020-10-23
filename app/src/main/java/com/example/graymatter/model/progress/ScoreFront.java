package com.example.graymatter.model.progress;

import com.example.graymatter.model.dataAccess.DataAccess;
import com.example.graymatter.model.dataAccess.social.GameSession;
import com.example.graymatter.model.dataAccess.social.UserInfoException;


import java.util.List;

/**
 * Class contains methods returning different kind of leaderboards with normated scores.
 * Methods renormates score every time called. For bigger databases these calls are demanding.
 * This class should be located at serverside and methods should be called sparsely.
 */
public class ScoreFront {

    private DataAccess da;

    public ScoreFront(DataAccess da){
        this.da = da;
    }

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a
     * particular gameType with only games played by the user marked as currentUser in local cache.
     * Cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs.
     * Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match
     * leaderboard position (offset by one) Normated scores are given as an int above or equal to
     * zero, below 1000. int userID can be used to receive additional information about the Player
     * of the matching GameSession.
     * Additional information retained from PlayerAccess.
     */
    public int[][] getSelectFriendTopScores(int resultTop, int resultLow, String gameType){
        List<Integer> friendIDs = null;
        try {
            friendIDs = da.getCurrentPlayer().getFriendUserIDs();
        } catch (UserInfoException e) {
            getSelectGlobalTopScores(resultTop,resultLow,gameType);
        }
        friendIDs.add(da.getCurrentPlayer().getUserID());
        return NormLeaderboards.getSelectGroupTopScores(getGameData(gameType), resultTop, resultLow, friendIDs);
    }

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a
     * particular gameType, cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs.
     * Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match
     * leaderboard position (offset by one) Normated scores are given as an int above or equal to
     * zero, below 1000. int userID can be used to receive additional information about the Player
     * of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public int[][] getSelectGlobalTopScores(int resultTop, int resultLow, String gameType){
        return NormLeaderboards.getSelectGlobalTopScores(getGameData(gameType), resultTop, resultLow);
    }


    /**
     * Returns a leaderboard of players and corresponding normated scores. Based on persona, a.k.a.
     * overall topscoring player.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param legalGameTypes A String of gameTypes, seperated by space " ". Retained from
     * GameStrings
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores. Ordered from
     * top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard
     * position. Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching
     * GameSession. Additional information retained from PlayerAccess.
     */
    public int[][] getSelectGlobalTopPersonas(int resultTop, int resultLow, String legalGameTypes){
        int[][] allData = getGlobalTopPersonas(legalGameTypes);
        return NormLeaderboards.getSelectGlobalTopPersonas(allData, resultTop, resultLow);

    }


    /**
     * Returns a leaderboard of players on current users friend list (current user according to
     * cache) and corresponding normated scores. Based on persona, a.k.a. overall topscoring player.
     * Friends rankings does not indicate complete playerbase relative ranking, however scores are
     * normated after entire playerbase.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param legalGameTypes A String of gameTypes, seperated by space " ".
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores. Ordered from
     * top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard
     * position. All userIDs on leaderboard matches usernames in friend list of currentPlayer in
     * DataAccess. Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching
     * GameSession. Additional information retained from PlayerAccess.
     * @throws UserInfoException if da attribute currentPlayer works incorrectly.
     */
    public int[][] getSelectFriendTopPersonas(int resultTop, int resultLow, String legalGameTypes) throws UserInfoException {
        int[][] allData = getGlobalTopPersonas(legalGameTypes);
        List<Integer> friendIDs = da.getCurrentPlayer().getFriendUserIDs();
        friendIDs.add(da.getCurrentPlayer().getUserID());
        return NormLeaderboards.getSelectFriendTopPersonas(allData, resultTop, resultLow, friendIDs);
    }

    /**
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
     * Sorts list of GameSessions based on score, numbers x,  0 <= x. Top Scores on high index,
     * low scores on low index.
     * @param bfGS list of GameSessions to sort.
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
     * Gets matrix with userIDs, scores, and gameIDs from database.
     * @param gameType accessed from a verified String factory where one String only corresponds
     *                 to a legal game type in database.
     * @return matrix with int[0] = userIDs, int[1] = scores and int[2] gameIDs.
     */
    private int[][] getGameData(String gameType){
        List<GameSession> gameSessions = da.getGameSessionsByType(gameType);
        int[][] gs = new int[3][gameSessions.size()];

        for (int i = 0; i < gameSessions.size(); i++) {
            gs[1][i] = gameSessions.get(i).getScore();
            gs[2][i] = gameSessions.get(i).getGameID();
        }
        gs[0] = findGameOwners(gs[2]);
        return gs;
    }


    /**
     * Finds mathing userIDs for input gameIDs.
     * @param gameIDs to find owners for.
     * @return array where indexes of content userIDs matches indexes of corresponding
     * input gameIDs, as in owners indexed as gameIDs.
     */
    private int[] findGameOwners(int[] gameIDs){
        int[] userIDs = new int[gameIDs.length];
        for (int i = 0; i < gameIDs.length; i++) {
            userIDs[i] = da.getGameIDOwner(gameIDs[i]);
        }

        return userIDs;
    }

    public static void setSignificantNumbers(int sigNum){
        NormLeaderboards.setSignificantNumber(sigNum);
    }
}
