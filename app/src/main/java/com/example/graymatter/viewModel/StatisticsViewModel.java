package com.example.graymatter.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.model.dataAccess.DataAccess;
import com.example.graymatter.model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.model.progress.ScoreFront;

public class StatisticsViewModel extends ViewModel {
    private String[] topFriendsUsernames = new String[]{"0"};
    private String[] topGlobalUsernames = new String[]{"0"};

    private int[] topFriendsUserImages, topGlobalUserImages;

    private int[] topFriendsUserScores = new int[]{0};
    private int[] topGlobalUserScores = new int[]{0};

    private int[][] friendsScores, globalScores;



    private DataAccess playerAccess;

    public void init(DataAccess dataAccess, String game){

        playerAccess = dataAccess;

        ScoreFront scoreFront = new ScoreFront(dataAccess);

        //friendsScores[1] is score, [2] is ID:s


        try {
            globalScores = scoreFront.getSelectGlobalTopScores(1,10, game);
        }catch (IllegalArgumentException e){
            return;
        }


        try {
            friendsScores = scoreFront.getSelectFriendTopScores(1,10, game);
        }catch (DataMapperException e){
            friendsScores = globalScores;
        }

        String[] friendsUsernames = new String[friendsScores[0].length];
        String[] globalUsenames = new String[globalScores[0].length];



        for (int i = 0; i < friendsScores[0].length; i++) {
            try {
                friendsUsernames[i] = playerAccess.getNonUserPlayer(friendsScores[0][i]).getUserName();
            }catch (DataMapperException e){
                friendsUsernames[i] = "TERMINATED";
            }
        }
        for (int i = 0; i < globalScores[0].length; i++) {
            try {
                globalUsenames[i] = playerAccess.getNonUserPlayer(globalScores[0][i]).getUserName();
            }catch (DataMapperException e){
                globalUsenames[i] = "TERMINATED";
            }
        }





        topFriendsUsernames = friendsUsernames;
        topFriendsUserScores = friendsScores[1];
        topFriendsUserImages = new int[]{1,2,3};

        topGlobalUsernames = globalUsenames;
        topGlobalUserScores = globalScores[1];
        topGlobalUserImages = new int[]{1,2,3};
    }


    public String[] getTopFriendsUsernames(){

        return topFriendsUsernames;
    }
    public int[] getTopFriendsUserScores(){

        return topFriendsUserScores;
    }
    public int[] getTopFriendsUserImages(){

        return topFriendsUserImages;
    }


    public String[] getTopGlobalUsernames(){

        return topGlobalUsernames;
    }
    public int[] getTopGlobalUserScores(){

        return topGlobalUserScores;
    }
    public int[] getTopGlobalUserImages(){

        return topGlobalUserImages;
    }

}
