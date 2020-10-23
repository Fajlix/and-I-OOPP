package com.example.graymatter.view;

import com.example.graymatter.model.dataAccess.DataAccess;

public interface FragmentChangeListener {
    void reactionTestClicked();
    void chimpTestClicked();
    void visualGameClicked();

    void friendsDialogClicked();
    void settingsDialogClicked();

    void logoutClicked();
    void changeEmailClicked();
    void changePasswordClicked();
    void ToHClicked();

    void notLoggedIn();
    void registerClicked();
    void backToProfile();

    //TODO Change name in interface or make another one?
    String getGame();
    DataAccess getDataAccess();
}
