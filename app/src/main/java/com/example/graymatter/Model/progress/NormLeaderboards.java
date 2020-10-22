package com.example.graymatter.Model.progress;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
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
     * @param scoresAllData int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs.
     * Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * @throws IllegalArgumentException if resultTop > resultLow, resultTop < 1 or resultLow <1.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectGroupTopScores(int[][] scoresAllData, int resultTop, int resultLow, List<Integer> userIDs) throws IllegalArgumentException {
        //check if the resultspan is legal
        if(!isResultSpanLegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        //sort and norm matrix
        int[][] normArraysWOwners = getGlobalTopScores(scoresAllData);
        //filter out non-friends
        int[][] friendTopScores = filterFriends(normArraysWOwners, 2, userIDs);
        //fix illegal resultspan
        resultLow = findResultsLow(resultLow, friendTopScores[0].length);
        //cut out the unwanted rankings and return
        return cutOutSelectedTopListPart(friendTopScores, resultTop, resultLow, 3); //TODO colUnCut till 0
    }

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a particular argument collection of game sessions, cut after argument input.
     * @param scoresAllData int[][] containing int[0] being a list of userIDs, int[1] normScores, int[2] gameIDs
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs.
     * Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * @throws IllegalArgumentException if resultTop > resultLow, resultTop < 1 or resultLow <1.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectGlobalTopScores(int[][] scoresAllData, int resultTop, int resultLow) throws IllegalArgumentException{
        //much similar to getSelectFriendTopScores. See above for closer commenting
        //check if the resultspan is legal
        if(!isResultSpanLegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        int[][] normArraysWOwners = getGlobalTopScores(scoresAllData);
        resultLow = findResultsLow(resultLow, normArraysWOwners[0].length);
        return cutOutSelectedTopListPart(normArraysWOwners, resultTop, resultLow, 3);
    }

    private static int[][] getGlobalTopScores(int[][] IDsAndScores){
        //below: normArrays[1] = normScores
        //sort and norm the results
        int[][] sortArrays = Sort.multRowSort(IDsAndScores, false, 1);
        sortArrays[1] = NormScore.normScores(sortArrays[1])[1];
        int[][] reverted = Sort.multRowSort(sortArrays, true, 1);
        return reverted;
    }

    /**
     * Returns a leaderboard of players and corresponding normated scores. Based on persona, a.k.a. overall topscoring player.
     * @param allData three-row matrix where int[0] contains userIDs, int[1] scores, int[2] gameIDs.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @return int[][] containing int[0] being a list of userIDs, int[1] normScores. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectGlobalTopPersonas(int[][] allData, int resultTop, int resultLow) throws IllegalArgumentException{
        //check if the resultspan is legal
        if(!isResultSpanLegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        int[][] normArrayWOwners = getGlobalTopPersonas(allData);
        resultLow = findResultsLow(resultLow, normArrayWOwners.length);
        return cutOutSelectedTopListPart(normArrayWOwners, resultTop, resultLow, 2);
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
    public static int[][] getSelectFriendTopPersonas(int[][] scoresAllData, int resultTop, int resultLow, List<Integer> userIDsOfInterest) throws IllegalArgumentException {
        //check if the resultspan is legal
        if(!isResultSpanLegal(resultTop, resultLow)){
            throw new IllegalArgumentException("Illegal resultspan!");
        }
        int[][] normArrayWOwners = getGlobalTopPersonas(scoresAllData);
        //cut out nonfriends from leaderboard
        int[][] justfriends = filterFriends(normArrayWOwners, 0, userIDsOfInterest);
        resultLow = findResultsLow(resultLow, justfriends.length);
        return cutOutSelectedTopListPart(normArrayWOwners, resultTop, resultLow, 2);
    }

    /**
     *
     * @param allData a gametypes, allData.length = 2*a, [a] = userIDs,  [a+1] = scores
     * @return matrix, int[1] norm scores
     */
    private static int[][] getGlobalTopPersonas(int[][] allData){
        int[][] normArray = new int[allData.length][];
        int newNAI = 0;
        for (int a = 0; a < allData.length; a = a+2) {
            int[][] gameData = new int[2][];
            gameData[0] = allData[a];
            gameData[1] = allData[a+1];
            //sorts and norms
            gameData = getGlobalTopScores(gameData);
            //remove all entries except one from same users
            gameData = removeAllButPersonalTopScores(gameData);
            normArray[newNAI] = gameData[0];
            normArray[newNAI+1] = gameData[1];
            newNAI +=2;
        }
        return normPersonas(normArray);
    }

    /**
     * Removes all but one top scoring entry from same user from leaderboard.
     * @param addArray: int[0] containing userIDs, int[1] containing normated scores
     * @return int[0] containing userIDs, int[1] containing normated scores
     */
    private static int[][] removeAllButPersonalTopScores(int[][] addArray) {
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
        //sort, norm, sort, return
        int[][] sortedScores = Sort.multRowSort(addedScores, false, 1);
        sortedScores[1] = NormScore.normScores(sortedScores[1])[1];
        return Sort.multRowSort(sortedScores, true, 1);
    }

    /**
     * Filter out rows from matrix where int[0][row] does not match an entry in current user´s (according to cache) friend list.
     * @param notJustFriends int matrix where notJustFriends[0] contains userIDs
     * @return int[notJustFriends.length][<length of current user´s friend list>], int[0] containing userIDs
     */ //TODO bake together friendsIDs and currentUSerID
    private static int[][] filterFriends(int[][] notJustFriends, int colWUserID, List<Integer> userIDsOfInterest) {
        int[][] justFriends = new int[notJustFriends.length][userIDsOfInterest.size()];
        int friendSpotsLeft = 0;
        for (int i = 0; i < notJustFriends[0].length; i++) {
            if(userIDsOfInterest.contains(notJustFriends[colWUserID][i])){
                for (int j = 0; j < notJustFriends.length; j++) {
                    justFriends[j][friendSpotsLeft] = notJustFriends[j][i];
                }
                friendSpotsLeft++;
            }
        }
        return justFriends;
    }

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


    private static boolean isResultSpanLegal(int resultTop, int resultLow){
        return resultLow >= resultTop
                && resultLow >= 1
                && resultTop >= 1;
    }

    private static int findResultsLow(int resultsLow, int listlength) {
        if(listlength < resultsLow){
            return listlength;
        }
        return resultsLow;
    }


}
