package com.example.graymatter.View;

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

import com.example.graymatter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener{

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Dialog friendsDialog;
    private Dialog settingsDialog;

    private String game;


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
      
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    public String getGame(){
        return game;
    }


    @Override
    public void reactionTestClicked() {
        //Checks if current fragment is StatisticsFragment, else it's GamesFragment
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = "Reaction Test";
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.reactionTestActivity);
    }

    @Override
    public void chimpTestClicked() {
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = "Chimp Test";
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.chimpGameActivity);
    }

    @Override
    public void visualGameClicked() {
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            game = "Visual Test";
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.visualGameFragment);
    }



    @Override
    public void friendsDialogClicked() {
        //friendsDialog = new FriendsDialog(this, );
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
        navController.navigate(R.id.toHFragment);
    }
}