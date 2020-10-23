package com.example.graymatter.model.progress;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * @author Aline
 * Class contains methods returning different kind of leaderboards with normated scores.
 * Methods renormates score every time called. For bigger databases these calls are demanding.
 * This class should be located at serverside and methods should be called sparsely.
 */
public class NormLeaderboards {


    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a particular argument collection of game sessions.
     * Only includes entries where gameIDs are related to userIDs in argument userIDs. Cut after argument input.
     * @param userIDs List of userIDs of people whose scores are to be included in the leaderboard.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param inputRes int[][] containing int[0] being a list of userIDs, int[1] scores, int[2] gameIDs
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs.
     * Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * @throws IllegalArgumentException if resultTop > resultLow, resultTop < 1 or resultLow <1.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectGroupTopScores(int[][] inputRes, int resultTop, int resultLow, List<Integer> userIDs) throws IllegalArgumentException {
        //check if the resultspan is legal
        if(isResultSpanIllegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        //sort and norm matrix
        int[][] nScoresArr = Sort.multRowSort(inputRes, true, 1);
        nScoresArr[1] = NormScore.normScores(nScoresArr[1])[1];
        //filter out non-members of group
        int[][] friendTopScores = filterUserIDs(nScoresArr, 0, userIDs);
        //fix resultLow if result span is larger than the amount of leaderboard entries
        resultLow = findResultsLow(resultTop, resultLow, friendTopScores[0].length);
        //cut out the unwanted rankings and return
        return cutOutSelectedTopListPart(friendTopScores, resultTop, resultLow, 0);
    }

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a particular argument collection of game sessions, cut after argument input.
     * @param inputRes int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs.
     * Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * @throws IllegalArgumentException if resultTop > resultLow, resultTop < 1 or resultLow <1.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectGlobalTopScores(int[][] inputRes, int resultTop, int resultLow) throws IllegalArgumentException{
        //check if the resultspan is legal
        if(isResultSpanIllegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        int[][] nScoresArr = Sort.multRowSort(inputRes, true, 1);
        nScoresArr[1] = NormScore.normScores(nScoresArr[1])[1];
        resultLow = findResultsLow(resultTop, resultLow, nScoresArr[0].length);
        return cutOutSelectedTopListPart(nScoresArr, resultTop, resultLow, 0);
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
    public static int[][] getSelectGlobalTopPersonas(int[][] inputRes, int resultTop, int resultLow) throws IllegalArgumentException{
        //check if the resultspan is legal
        if(isResultSpanIllegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        int[][] topPersonas = getGlobalTopPersonas(inputRes);
        resultLow = findResultsLow(resultTop, resultLow, topPersonas[0].length);
        return cutOutSelectedTopListPart(topPersonas, resultTop, resultLow, 0);
    }


    /**
     * Returns a leaderboard of players on current users friend list (current user according to cache) and corresponding normated scores. Based on persona, a.k.a. overall topscoring player.
     * Friends rankings does not indicate complete playerbase relative ranking, however scores are normated after entire playerbase.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param legalGameTypes A String of gameTypes, seperated by space " ".
     * @return int[][] containing int[0] being a list of userIDs in currentPlayers friendbase, int[1] normScores. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * @throws UserInfoException
     */
    public static int[][] getSelectFriendTopPersonas(int[][] scoresAllData, int resultTop, int resultLow, List<Integer> userIDs) throws IllegalArgumentException {
        //check if the resultspan is legal
        if(isResultSpanIllegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        //sort, norm, make personas, sort and norm personas
        int[][] topPersonas = getGlobalTopPersonas(scoresAllData);
        //cut out nonfriends from leaderboard
        int[][] justfriends = filterUserIDs(topPersonas, 0, userIDs);
        //fixes resultLow if result span is larger than the amount of leaderboard entries
        resultLow = findResultsLow(resultTop, resultLow, justfriends.length);
        //cut out the unwanted rankings and return
        return cutOutSelectedTopListPart(topPersonas, resultTop, resultLow, 0);
    }

    /**
     * Note that this method rewards consistent high results over few high results.
     * This is since we normate before we norm the results.
     * This algorithm benefits frequent players.
     * @param inputRes a gametypes, inputRes.length = 2*a, [a] = userIDs,  [a+1] = scores
     * @return matrix, int[1] norm scores
     */
    private static int[][] getGlobalTopPersonas(int[][] inputRes){
        int[][] addNScores = new int[inputRes.length][];
        //index of next empty column
        int newNAI = 0;
        int[][] gameData = new int[2][];
        for (int a = 0; a < inputRes.length; a = a+2) {
            //put data from one game into a seperate matrix
            gameData[0] = inputRes[a];
            gameData[1] = inputRes[a+1];
            //sort and norm
            Sort.multRowSort(gameData, true, 1);
            gameData[1] = NormScore.normScores(gameData[1])[1];
            //remove all entries except one from same users
            gameData = removeAllButPersonalTopScores(gameData);
            addNScores[newNAI] = gameData[0];
            addNScores[newNAI+1] = gameData[1];
            newNAI +=2;
        }
        return normPersonas(addNScores);
    }

    /**
     * Removes all but one top scoring entry from same user from leaderboard.
     * @param addArray: int[0] containing userIDs, int[1] containing normated scores
     * @return int[0] containing userIDs, int[1] containing normated scores
     */
    private static int[][] removeAllButPersonalTopScores(int[][] addArray) {
        //here we use a map to avoid many for-loops
        //key is userID, value norm score
        Map<Integer, Integer> notedIDs = new HashMap<>();
        for (int i = 0; i < addArray[0].length; i++) {
            //if already noted result from user, check if the new result is higher and if so let it replace the old
            if(notedIDs.containsKey(addArray[0][i])){
                if(addArray[1][i] > notedIDs.get(addArray[0][i])){
                    notedIDs.remove(addArray[0][i]);
                    notedIDs.put(addArray[0][i], addArray[1][i]);
                }
                //else add it in map
            } else if (addArray[0][i] != 0){
                notedIDs.put(addArray[0][i], addArray[1][i]);
            }
        }
        //convert map to two-row matrix
        int[][] resultsMatrix = new int[2][notedIDs.size()];
        int count = 0;
        for (Map.Entry<Integer, Integer> entry:notedIDs.entrySet()) {
            resultsMatrix[0][count] = entry.getKey();
            resultsMatrix[1][count] = entry.getValue();
            count++;
        }
        return resultsMatrix;
    }

    /**
     * @param allGameTypes int[i] = userIDs, int[i+1] normated scores, columns div. after gametype
     * @return int[][]: int[0] containing userIDs, int[1] containing normated scores, sorted from top results at low index
     */
    private static int[][] normPersonas(int[][] allGameTypes){
        //check how many users with reg. results
        List<Integer> userIDs = new ArrayList<>();
        for (int i = 0; i < allGameTypes.length; i+=2) {
            for (int j = 0; j < allGameTypes[i].length; j++) {
                if(!userIDs.contains(allGameTypes[i][j])){
                    userIDs.add(allGameTypes[i][j]);
                }
            }
        }
        if (userIDs.contains(0)){ //shouldn't be needed
            userIDs.remove(0);
        }

        //squash matrix to two columns, add together scores paired with owner userID.
        int[][] addedScores = new int[2][userIDs.size()];
        //index for next empty row in addedScores
        int freeIndex = 0;
        //for every pair of columns aka every gametype
        for (int i = 0; i < allGameTypes.length; i+=2) {
            //for every entry on that game
            for (int j = 0; j < allGameTypes[i].length; j++) {
                //for every entry in addedScores, check if it contains the userID and if so assign them that score
                boolean found = false;
                for (int k = 0; k < addedScores[0].length; k++) {
                    if(addedScores[0][k] != 0
                    && addedScores[0][k] == allGameTypes[i][j]){
                        addedScores[1][k] += allGameTypes[i+1][j];
                        found = true;
                        break;
                    }
                }
                //else put userID on addedScore[0] and first score on addedScore[1] in new entry
                if(!found){
                    addedScores[0][freeIndex] = allGameTypes[i][j];
                    addedScores[1][freeIndex] = allGameTypes[i+1][j];
                    freeIndex++;
                }
            }
        }
        //sort, norm, return
        int[][] sortedScores = Sort.multRowSort(addedScores, true, 1);
        sortedScores[1] = NormScore.normScores(sortedScores[1])[1];
        return sortedScores;
    }

    /**
     * Filter out rows from matrix where int[0][row] does not match an entry in current user´s (according to cache) friend list.
     * @param notJustFriends int matrix where notJustFriends[0] contains userIDs
     * @return int[notJustFriends.length][<length of current user´s friend list>], int[0] containing userIDs
     */
    private static int[][] filterUserIDs(int[][] notJustFriends, int colWUserID, List<Integer> userIDsOfInterest) {
        int[][] justFriends = new int[notJustFriends.length][userIDsOfInterest.size()];
        int friendSpotsLeft = 0;
        for (int i = 0; i < notJustFriends[0].length; i++) {
            if(friendSpotsLeft >= userIDsOfInterest.size())break;
            if(userIDsOfInterest.contains(notJustFriends[colWUserID][i])){
                for (int j = 0; j < notJustFriends.length; j++) {
                    justFriends[j][friendSpotsLeft] = notJustFriends[j][i];
                }
                friendSpotsLeft++;
            }
        }
        return justFriends;
    }

    /**
     * Cuts out unwanted parts of Top List.
     * @param unCut
     * @param resultTop
     * @param resultsLow
     * @param colUnCut desired width of returned matrix.
     * If colUnCut is 0, returned width will be equal to unCut.length
     * @return
     */
    private static int[][] cutOutSelectedTopListPart(int[][] unCut, int resultTop, int resultsLow, int colUnCut){
        if (colUnCut == 0){
            colUnCut = unCut.length;
        }
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


    private static boolean isResultSpanIllegal(int resultTop, int resultLow){
        return resultLow < resultTop
                || resultLow < 1
                || resultTop < 1;
    }

    /**
     * Fixes resultLow if result span is larger than the amount of leaderboard entries.
     */
    private static int findResultsLow(int resultTop, int resultsLow, int listlength) {
        if(listlength < resultsLow-resultTop+1){
            return listlength;
        }
        return resultsLow;
    }


    public static void setSignificantNumber(int sigNum) {
        NormScore.setSignificantNumbers(sigNum);
    }
}
