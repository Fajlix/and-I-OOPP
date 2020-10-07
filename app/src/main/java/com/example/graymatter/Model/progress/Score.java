package com.example.graymatter.Model.progress;

public class Score {

    GameSessionAccess gsa;

    public static int normScore(int unNorm, String gameType){

        //check somewhere if there are new games, if not use old normScores? would have to be saved in db, might be dumb
        int normScore;
        int[] distribution = gsa.getAllScores();
        int[] sorted = Sort.sort(distribution);
        //middle one has score 500
        //calculate "spots" based on the amount of scores, spots being score if 50th percentile = 500 points
        //then pop them out two at the time, one below, one above?
        return normScore;
    }


}
