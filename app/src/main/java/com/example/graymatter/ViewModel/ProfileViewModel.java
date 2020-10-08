package com.example.graymatter.ViewModel;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Social.PlayerMapper;
import com.example.graymatter.View.Fragments.ProfileFragment;

public class ProfileViewModel extends ViewModel {

    String[] friends;
    int[] friendsImages;

    PlayerMapper playerMapper;


    public void init(PlayerMapper playerMapper){
       this.playerMapper = playerMapper;



    }


    public void friendAdded(){

    }

    public void friendRemoved(){

    }



}
