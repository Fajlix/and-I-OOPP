package com.example.graymatter.Model.progress;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * Class contains methods returning different kind of leaderboards with normated scores.
 * Methods renormates score every time called. For bigger databases these calls are demanding. This class should be located at serverside and methods should be called sparsely.
 */
public class NormLeaderboards {

    /**
     * Returns a leaderboard of gamesessions, corresponding normated scores and players from a particular gameType, with only games played by the user marked as currentUser in local cache. Cut after argument input.
     * @param resultTop Top placement, not index, beginning the span of Map rows to return.
     * @param resultLow Low placement, not index, ending the span of Map rows to return.
     * @param gameType String retained from game class representing the type of the game.
     * @return int[][] containing int[0] being a list of gameIDs, int[1] normScores, int[2] userIDs. Ordered from top scores in low indexes to low scores in high indexes. Indexes does not match leaderboard position.
     * Normated scores are given as an int above or equal to zero, below 1000.
     * int userID can be used to receive additional information about the Player of the matching GameSession. Additional information retained from PlayerAccess.
     */
    public static int[][] getSelectFriendTopScores(int[][] IDandScoresAndUserIDs, int resultTop, int resultLow, int userID, List<Integer> friendIDs) {
        //check if the resultspan is legal
        if(resultLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        //get sorted matrix with gameowners, normated topscores and gameIDs
        int[][] normArraysWOwners = getGlobalTopScores(IDandScoresAndUserIDs, userID, friendIDs);
        //filter out non-friends
        int[][] friendTopScores = filterFriends(normArraysWOwners, 2, friendIDs, userID);
        //check for illegal resultspan
        resultLow = legalResultSpan(resultLow, friendTopScores[0].length);
        //cut out the unwanted rankings and return
        return cutOutSelectedTopListPart(friendTopScores, resultTop, resultLow, 3);
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
    public static int[][] getSelectGlobalTopScores(int resultTop, int resultLow, String gameType){
        //much similar to getSelectFriendTopScores. See above for closer commenting
        if(resultLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        int[][] normArraysWOwners = getGlobalTopScores(gameType);
        resultLow = legalResultSpan(resultLow, normArraysWOwners[0].length);
        return cutOutSelectedTopListPart(normArraysWOwners, resultTop, resultLow, 3);
    }

    private static int[][] getGlobalTopScores(int[][] IDsAndScores, List<Integer> owners){
        //below: normArrays[0] = gameIDs, normArrays[1] = normScores, normArrays[2] = ownerUserIDs
        //sort and norm the results
        int[][] sortArrays = Sort.multRowSort(IDsAndScores, false, 1);
        sortArrays[1] = NormScore.normScores(sortArrays[1])[1];

        int[][] reverted = Sort.multRowSort(sortArrays, true, 1);
        //find owners of gameIDs
        int[][] normArraysWOwners = new int[3][];
        //first two columns are copied from normArrays
        normArraysWOwners[0] = reverted[0];
        normArraysWOwners[1] = reverted[1];
        normArraysWOwners[2] = findGameOwners(normArrays[0]);
        return normArraysWOwners;
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
    public static int[][] getSelectGlobalTopPersonas(int resultTop, int resultLow, String legalGameTypes){
        if(resultLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        int[][] normArrayWOwners = getGlobalTopPersonas(legalGameTypes);
        resultLow = legalResultSpan(resultLow, normArrayWOwners[0].length);
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
    public static int[][] getSelectFriendTopPersonas(int resultTop, int resultLow, String legalGameTypes) throws UserInfoException {
        if(resultLow < resultTop){
            throw new IllegalArgumentException("Top placement can not be below low placement!");
        }
        int[][] normArrayWOwners = getGlobalTopPersonas(legalGameTypes);
        //cut out nonfriends from leaderboard
        int[][] justfriends = filterFriends(normArrayWOwners, 0);
        resultLow = legalResultSpan(resultLow, justfriends.length);
        return cutOutSelectedTopListPart(normArrayWOwners, resultTop, resultLow, 2);
    }

    /**
     *
     * @param legalGameTypes A String of gameTypes, seperated by space " ". Retained from
     * @return twocolumn matrix, int[0] containing userIDs, int[1] norm scores
     */
    private static int[][] getGlobalTopPersonas(String legalGameTypes){
        String[] gameTypes = legalGameTypes.split(" ");
        int[][] normArray = new int[gameTypes.length*2][];
        int newNAI = 0;
        for (String gameType : gameTypes) {
            //normArray[0] gameIDs, normArray[1] norm scores
            int[][] addArray = findAndNorm(gameType);
            //int[][] addArray = new int[2][];
            //gameSession removed from index 0, leaves room for userIDs
            addArray[0] = findGameOwners(addArray[0]);
            //remove all entries except one from same users
            addArray = removeAllButPersonalTopScores(addArray);
            int sI = normArray.length;
            //put addArray as new columns in normArray
            normArray[newNAI] = addArray[0];
            normArray[newNAI+1] = addArray[1];
            newNAI += 2;
        }
        return normPersonas(normArray);
    }

    /**
     * Removes all but one top scoring entry from same user from leaderboard.
     * @param addArray: int[1] containing userIDs, int[1] containing normated scores
     * @return int[1] containing userIDs, int[1] containing normated scores
     */
    private static int[][] removeAllButPersonalTopScores(int[][] addArray) {
        //key is gameID, value norm score
        Map<Integer, Integer> notedIDs = new HashMap<>();
        for (int i = 0; i < addArray[0].length; i++) {
            //if already noted result from user, check if the new result is higher and if so let it replace the old
            if(notedIDs.containsKey(addArray[0][i])){
                if(addArray[1][i] > notedIDs.get(addArray[0][i])){
                    notedIDs.remove(addArray[0][i]);
                    notedIDs.put(addArray[0][i], addArray[1][i]);
                }
                //else add it in map
            } else {
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
        //check length of longest column
        //TODO reuse this list later in method
        List<Integer> userIDs = new ArrayList<>();
        for (int i = 0; i < allGameTypes.length; i+=2) {
            for (int j = 0; j < allGameTypes[i].length; j++) {
                if(!userIDs.contains(allGameTypes[i][j])){
                    userIDs.add(allGameTypes[i][j]);
                }
            }
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
        //switch rowplacement before sort TODO just noo about the switcharoo
        int[][] switchedRows = new int[2][];
        switchedRows[0] = addedScores[1];
        switchedRows[1] = addedScores[0];
        int[][] sortedScores = Sort.multRowSort(switchedRows, false, 0);
        //switch back
        int[][] toReturn = new int[2][];
        /*int[][] reverse = new int[2][];
        //TODO arrays shouldn't have to be reverted
        for (int i = 0; i < switchedRows[0].length; i++) {
            reverse[0][i] = sortedScores[0][sortedScores[0].length-i-1];
            reverse[1][i] = sortedScores[1][sortedScores[0].length-i-1];
        }*/
        toReturn[1] = NormScore.normScores(sortedScores[0])[1];
        toReturn[0] = sortedScores[1];
        return Sort.multRowSort(toReturn, true, 1);

    }

    /**
     * Filter out rows from matrix where int[0][row] does not match an entry in current user´s (according to cache) friend list.
     * @param notJustFriends int matrix where notJustFriends[0] contains userIDs
     * @return int[notJustFriends.length][<length of current user´s friend list>], int[0] containing userIDs
     */
    private static int[][] filterFriends(int[][] notJustFriends, int colWUserID, List<Integer> friendIDs, int currentUserID) {
        int[][] justFriends = new int[notJustFriends.length][friendIDs.size()];
        for (int i = 0; i < notJustFriends[0].length; i++) {
            if(friendIDs.contains(notJustFriends[colWUserID][i])
                    || currentUserID == notJustFriends[colWUserID][i]) {
                for (int j = 0; j < notJustFriends.length; j++) {
                    justFriends[j][i] = notJustFriends[j][i];
                }
            }
        }
        return justFriends;


        /*
        int[][] justFriends = new int[notJustFriends.length][notJustFriends[0].length];
        int friendGameCount = 0;
        for (int i = 0; i < notJustFriends[0].length; i++) {
            try {
                if(pa.getCurrentPlayer().getFriendUserIDs().contains(notJustFriends[0][i]) //TODO why not colWUserID???
                || pa.getCurrentPlayer().getUserID() == notJustFriends[colWUserID][i]){
                    for (int j = 0; j < notJustFriends.length; j++) {
                        justFriends[j][i] = notJustFriends[j][i];
                    }
                    friendGameCount++;
                }
            } catch (UserInfoException e){
                e.printStackTrace();
            }
        }
        int[][] friendsWOZeroes = new int[3][friendGameCount];
        int count = 0;
        for (int i = 0; i < friendGameCount; i++){
            if(justFriends[colWUserID][i] != 0){
                for (int j = 0; j < notJustFriends.length; j++) {
                    friendsWOZeroes[j][count] = notJustFriends[j][i];
                }
                count++;
            }
        }
        return friendsWOZeroes;
        */
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


    private static int legalResultSpan(int resultsLow, int listlength) throws IllegalArgumentException{
        if(listlength < resultsLow){
            return listlength;
        }
        return resultsLow;
    }


}
