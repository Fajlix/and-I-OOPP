package com.example.graymatter.ViewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Social.PlayerAccess;

public class StatisticsViewModel extends ViewModel {
    private Context context;

    private String[] usernames;
    private int[] userImages, userScores;

    private PlayerAccess playerAccess;

    public void init(Context context){
        this.context = context;
        playerAccess = new PlayerAccess("src/main/assets/testplayers.json", context);
    }

    public void update(String game){

    }

    public String[] getUsersnames(){

        return  null;
    }
    public int[] getUserImages(){

        return  null;
    }
    public int[] getUserScores(){

        return  null;
    }


}
