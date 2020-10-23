package com.example.graymatter.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.progress.ScoreFront;
import com.example.graymatter.View.FragmentChangeListener;

import java.util.ArrayList;


public class StatisticsViewModel extends ViewModel {
    private Context context;

    private String[] topFriendsUsernames, topFriendsUserScores, topGlobalUsernames, topGlobalUserScores;
    private int[] topFriendsUserImages, topGlobalUserImages;
    private int[][] friendsScores, globalScores;



    private DataAccess playerAccess;

    public void init(Context context, DataAccess dataAccess, String game){
        this.context = context;

        playerAccess = dataAccess;

        //friendsScores[1] is score, [2] is ID:s
        friendsScores = ScoreFront.getSelectFriendTopScores(1,10, game); //TODO not sure if right
        globalScores = ScoreFront.getSelectGlobalTopScores(1,10, game);


        String[] friendsUsernames = new String[10];
        String[] globalUsenames = new String[10];


        for (int i = 0; i < 10; i++) {
            //friendsUsernames[i] = playerAccess.g
        }




        topFriendsUsernames = new String[]{"123", "456", "1234"};
        topFriendsUserScores = new String[]{"hej", "så", "okej"};
        topFriendsUserImages = new int[]{1,2,3};

        topGlobalUsernames = new String[]{"123", "456", "1234"};
        topGlobalUserScores = new String[]{"hej", "så", "okej"};
        topGlobalUserImages = new int[]{1,2,3};
    }


    public String[] getTopFriendsUsernames(){

        return topFriendsUsernames;
    }
    public String[] getTopFriendsUserScores(){

        return topFriendsUserScores;
    }
    public int[] getTopFriendsUserImages(){

        return topFriendsUserImages;
    }


    public String[] getTopGlobalUsernames(){

        return topGlobalUsernames;
    }
    public String[] getTopGlobalUserScores(){

        return topGlobalUserScores;
    }
    public int[] getTopGlobalUserImages(){

        return topGlobalUserImages;
    }

}
