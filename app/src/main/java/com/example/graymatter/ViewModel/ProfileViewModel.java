package com.example.graymatter.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Social.PlayerAccess;

public class ProfileViewModel extends ViewModel {

    String[] friends;
    int[] friendsImages;

    private PlayerAccess playerAccess;

    public void init(String jsonStr){
        playerAccess = new PlayerAccess(jsonStr);
    }

    public String[] getFriends(){
        String[] names = new String[playerAccess.getFriends().size()];
        for (int i = 0; i < playerAccess.getFriends().size(); i++) {
            names[i] = playerAccess.getFriends().get(i).getUserName();
        }

        return names;
    }

    public void friendAdded(){

    }

    public void friendRemoved(){

    }



    public boolean isLoggedIn(){
        return playerAccess.isLoggedIn();
    }

    public void login(String username, String password){

    }
    public void register(String username, String email, String password){

    }




    //Settings
    public void logoutUser(){

    }
    public void changeEmail(String currentEmail, String newEmail, String confirmNewEmail){


    }
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword){


    }



}
