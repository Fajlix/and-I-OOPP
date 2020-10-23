package com.example.graymatter.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.progress.ScoreFront;
import com.example.graymatter.View.FragmentChangeListener;

import java.util.ArrayList;


public class StatisticsViewModel extends ViewModel {
    private Context context;

    private String[] topFriendsUsernames, topGlobalUsernames;
    private int[] topFriendsUserImages, topGlobalUserImages, topFriendsUserScores, topGlobalUserScores;
    private int[][] friendsScores, globalScores;



    private DataAccess playerAccess;

    public void init(Context context, DataAccess dataAccess, String game){
        this.context = context;

        playerAccess = dataAccess;

        ScoreFront scoreFront = new ScoreFront(dataAccess);

        //friendsScores[1] is score, [2] is ID:s



        globalScores = scoreFront.getSelectGlobalTopScores(1,10, game);
        try {
            friendsScores = scoreFront.getSelectFriendTopScores(1,10, game);
        }catch (DataMapperException e){
            friendsScores = globalScores;
        }

        String[] friendsUsernames = new String[10];
        String[] globalUsenames = new String[10];



        for (int i = 0; i < 10; i++) {
            try {
                friendsUsernames[i] = playerAccess.getNonUserPlayer(friendsScores[0][i]).getUserName();
            }catch (DataMapperException e){
                friendsUsernames[i] = "TERMINATED";
            }
            try {
                globalUsenames[i] = playerAccess.getNonUserPlayer(globalScores[0][i]).getUserName();
            }catch (DataMapperException e){
                friendsUsernames[i] = "TERMINATED";
            }
        }



        topFriendsUsernames = friendsUsernames;
        topFriendsUserScores = friendsScores[2];
        topFriendsUserImages = new int[]{1,2,3};

        topGlobalUsernames = globalUsenames;
        topGlobalUserScores = globalScores[2];
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
