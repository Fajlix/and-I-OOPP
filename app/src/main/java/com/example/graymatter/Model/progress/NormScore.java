package com.example.graymatter.Model.progress;


/**
 * Class contains static methods use for normating numbers.
 */
public class NormScore { //TODO this should be renamed NormNumber

    private static int ACCURACY = 3;
    /**
     * Method normates scores by associating each of them with a number 1-1000 representing the percentile the score performs in.
     * @param scores sorted with top scores at high indexes
     * @return int[][]: int[0] original scores, int[1] normated scores, both sorted with low scores at low indexes and v.v.
     */
    public static int[][] normScores(int[] scores){ //TODO flip it this is dumb
        //what part of the gameDB does each game represent?
        double chunks = ((double)Math.pow(10, ACCURACY))/(double)scores.length;
        int[] normScores = new int[scores.length];
        int sameCount = 1;
        for (int i = scores.length-2; i >= 0; i--) {
            //if this score is not the same as the last one, the last score and all equal to the last score can get a normated score
            if (scores[i+1] != scores[i]){
                //if the number was one of a kind the percentile is just the amount of scores performing worse*the percentage each score is of scores
                if(sameCount == 1){
                    normScores[i+1] = (int)(chunks * (scores.length-i-2));
                    //if multiple numbers scored the same, their normated score is the normated score if there were only one score + the percentage these scores are of scores (the list)/2
                } else{
                    double span = chunks*sameCount;
                    int spanMid = (int)(chunks*(scores.length-i-sameCount-1)+(span /2));
                    for (int j = 0; j < sameCount; j++) {
                        normScores[i+j+1] = spanMid;
                    }
                    sameCount = 1;
                }
            } else {
                sameCount++;
            }
        }
        //the very last level of scores need to be normated
        normScores[0] = (int)chunks* (scores.length-1);
/*
        int[] normScores = new int[scores.length];
        int sameCount = 1;
        for (int i = 1; i < scores.length; i++) {
            //if this score is not the same as the last one, the last score and all equal to the last score can get a normated score
            if (scores[i-1] != scores[i]){
                //if the number was one of a kind the percentile is just the amount of scores performing worse*the percentage each score is of scores
                if(sameCount == 1){
                    normScores[i-1] = (int)(chunks * (i-1));
                    //if multiple numbers scored the same, their normated score is the normated score if there were only one score + the percentage these scores are of scores (the list)/2
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
        //the very last level of scores need to be normated
        normScores[scores.length-1] = (int)chunks* (scores.length-1);



*/

        //normated scores are paired with argument unnormated scores //TODO this could loop through a wider matrix
        int[][] scoresAndNormatedScores = new int[2][scores.length];
        scoresAndNormatedScores[0] = scores;
        scoresAndNormatedScores[1] = normScores;
        return scoresAndNormatedScores;
    }

    public static void setACCURACY(int decimals){
        ACCURACY = decimals;
    }
}
