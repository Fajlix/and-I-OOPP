package com.example.graymatter.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.graymatter.model.game.GameStrings;
import com.example.graymatter.model.dataAccess.DataAccess;
import com.example.graymatter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener{

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Dialog friendsDialog;
    private Dialog settingsDialog;

    private String game;
    private DataAccess dataAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Configures bottom navigation
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        //Listens to navigation and hides/shows the bottomNavigationView depending on fragment
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               if(destination.getId() == R.id.reactionTestActivity || destination.getId() == R.id.chimpGameActivity ||
                       destination.getId() == R.id.visualGameFragment || destination.getId() == R.id.toHFragment){
                   bottomNavigationView.setVisibility(View.GONE);
               }
               else bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        dataAccess = new DataAccess(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @Override
    public String getGame(){
        return game;
    }
    @Override
    public DataAccess getDataAccess() {
        return dataAccess;
    }



    @Override
    public void reactionTestClicked() {
        //Checks if current fragment is StatisticsFragment, else it's GamesFragment
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = GameStrings.getReactionString();
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.reactionTestActivity);
    }

    @Override
    public void chimpTestClicked() {
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = GameStrings.getChimpString();
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.chimpGameActivity);
    }

    @Override
    public void visualGameClicked() {
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = GameStrings.getMemoryString();
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.visualGameFragment);
    }



    @Override
    public void friendsDialogClicked() {
        friendsDialog = new FriendsDialog(this);
        friendsDialog.setContentView(R.layout.dialog_friends);

        friendsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        friendsDialog.show();
    }

    @Override
    public void settingsDialogClicked() {
        settingsDialog = new SettingsDialog(this);
        settingsDialog.setContentView(R.layout.dialog_settings);

        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingsDialog.show();
    }



    @Override
    public void logoutClicked() {
        settingsDialog.dismiss();
        navController.navigate(R.id.profileFragment);  //TODO maby something else
    }

    @Override
    public void changeEmailClicked() {
        settingsDialog.dismiss();
        navController.navigate(R.id.changeEmailFragment);
    }

    @Override
    public void changePasswordClicked() {
        settingsDialog.dismiss();
        navController.navigate(R.id.changePasswordFragment);
    }

    @Override
    public void ToHClicked() {
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = GameStrings.getTowerString();
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.toHFragment);

    }

    @Override
    public void notLoggedIn() {
        navController.navigate(R.id.loginFragment);
    }

    @Override
    public void registerClicked() {
        navController.navigate(R.id.registerFragment);
    }

    @Override
    public void backToProfile() {
        navController.navigate(R.id.profileFragment);
    }
}