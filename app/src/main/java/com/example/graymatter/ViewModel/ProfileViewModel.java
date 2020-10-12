package com.example.graymatter.ViewModel;

import android.content.Context;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Social.Player;
import com.example.graymatter.Social.PlayerAccess;
import com.example.graymatter.Social.PlayerMapper;
import com.example.graymatter.View.Fragments.ProfileFragment;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    String[] friends;
    int[] friendsImages;
    Context context;

    private PlayerAccess playerAccess;

    public void init(Context context){
        this.context = context;
        playerAccess = new PlayerAccess("src/main/assets/testPlayers.json", context);
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
