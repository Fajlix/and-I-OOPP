package com.example.graymatter.ViewModel;

import android.content.Context;

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



    public String getUsername(){
        return playerAccess.getCurrentPlayer().getUserName();
    }
    public String getEmail() throws UserInfoException {
        return playerAccess.getCurrentPlayer().getEmail();
    }



    public boolean isLoggedIn(){
        return playerAccess.isLoggedIn();
    }

    /**
     * This method is called from the gui when the user logs in
     * @param username the entered username
     * @param password the entered password
     * @throws UserInfoException when username/password is wrong
     */
    public void login(String username, String password) throws UserInfoException {
        playerAccess.logIn(username,password);
    }
    public void register(String username, String email, String password) throws UserInfoException {
        playerAccess.createNewAccountAndLogIn(email,password,username);
    }




    //Settings
    public void logoutUser(){
        playerAccess.logOut();
    }
    public void changeEmail(String newEmail, String confirmNewEmail, String password) throws UserInfoException {
        playerAccess.changeEmail(password,newEmail);

    }
    public void changePassword(String currentPassword, String newPassword, String confirmNewPassword) throws UserInfoException {
        playerAccess.changePassword(currentPassword,newPassword);

    }




}
