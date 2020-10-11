package com.example.graymatter.Model.progress;

import com.example.graymatter.Model.dataAccess.GameSessionAccess;
import com.example.graymatter.Model.dataAccess.social.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NormScore {

    private static GameSessionAccess gsa;

    /**
     * "unfair"
     * @param unNorm
     * @param gameType
     * @return
     */

    public static void normScore(int unNorm, String gameType){
/*
        //check somewhere if there are new games, if not use old normScores? would have to be saved in db, might be dumb
        int normScore = 0;
        List<GameSession> list = gsa.getGameSessionsByType(gameType);
        int [] distribution = new int[list.size()];
        for (int i = 0; i < distribution.length; i++){
            distribution[i] = list.get(i).getScore();
        }
        int[] sorted = Sort.sort(distribution);
        //find unNorms pos. in this array
        for (int i = 0; i < sorted.length; i++) {
            if (unNorm == sorted[i]){
                normScore = sorted[i];
                return normScore;
            } else if (unNorm < sorted[i]){
                double chunks = 1000.0 /(double)sorted.length;
                normScore = (int)(chunks * (double)i);
            }
        }
        double average = findAverage(sorted);
        double standardD = findStandardDeviation(average, sorted);

        //middle one has score 500
        int midPos = sorted.length / 2; //TODO idk rounds up
        //every score same as
        //calculate "spots" based on the amount of scores, spots being score if 50th percentile = 500 points
        //then pop them out two at the time, one below, one above?
        return normScore;
        */
    }


    public static int[] normScores(int[] scores){
        double chunks = 1000.0 /(double)scores.length;
        int[] normScores = new int[scores.length];
        int sameCount = 1;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i-1] != scores[i]){
                if(sameCount == 1){
                    normScores[i-1] = (int)(chunks * (i-1));
                } else{
                    double span = chunks*sameCount;
                    int spanMid = (int)((span /2)+chunks*(i-sameCount));
                    for (int j = 0; j < sameCount; j++) {
                        normScores[i-j-1] = spanMid;
                    }
                    sameCount = 1;
                }
            } else {
                sameCount++;
            }
        }
        normScores[scores.length-1] = (int)chunks* (scores.length-1);
        return normScores;
    }

    private static double findAverage(int [] array){
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return ((double)sum / (double)array.length);
    }

    private static double findStandardDeviation(double average, int[] sorted) {
        return 0.0;
    }


}
