package com.example.graymatter.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;


public class ProfileViewModel extends ViewModel {

    String[] friends;
    int[] friendsImages;

    private DataAccess playerAccess;

    public void init(DataAccess playerAccess){
        this.playerAccess = playerAccess;
    }

    public String[] getFriends() throws UserInfoException {
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


    //Settings
    public void logoutUser(){

    }
    public void changeEmail(String currentEmail, String newEmail, String confirmNewEmail){


    }
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword){


    }



}
