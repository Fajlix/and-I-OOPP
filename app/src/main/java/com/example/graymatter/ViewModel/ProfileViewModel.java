package com.example.graymatter.ViewModel;

import android.content.Context;


import androidx.lifecycle.ViewModel;


import com.example.graymatter.Social.PlayerAccess;

public class ProfileViewModel extends ViewModel {

    String[] friends;
    int[] friendsImages;
    private Context context;

    private PlayerAccess playerAccess;

    public void init(Context context){
        this.context = context;
        playerAccess = new PlayerAccess("src/main/assets/testplayers.json", context);
    }

    public String[] getFriends(){
        String[] names = new String[playerAccess.getFriends(context).size()];
        for (int i = 0; i < playerAccess.getFriends(context).size(); i++) {
            names[i] = playerAccess.getFriends(context).get(i).getUserName();
        }

        return names;
    }

    public void friendAdded(){

    }

    public void friendRemoved(){

    }



}
