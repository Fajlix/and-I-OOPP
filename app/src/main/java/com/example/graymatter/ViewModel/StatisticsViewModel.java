package com.example.graymatter.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.dataAccess.DataAccess;


public class StatisticsViewModel extends ViewModel {
    private Context context;

    private String[] topFriendsUsernames, topFriendsUserScores, topGlobalUsernames, topGlobalUserScores;
    private int[] topFriendsUserImages, topGlobalUserImages;

    private DataAccess playerAccess;

    public void init(Context context, String game){
        this.context = context;

        if(game.equals("Reaction Test")){
            //TODO something with Social
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
