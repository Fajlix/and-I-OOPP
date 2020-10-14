package com.example.graymatter.View;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.PagerAdapter;

import com.example.graymatter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Dialog friendsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendsDialog = new Dialog(this);

        //Configures bottom navigation
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        //Listens to navigation and hides/shows the bottomNavigationView depending on fragment
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               if(destination.getId() == R.id.reactionTestActivity || destination.getId() == R.id.chimpGameActivity || destination.getId() == R.id.visualGameFragment){
                   bottomNavigationView.setVisibility(View.GONE);
               }
               else bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

    }



    @Override
    public void reactionTestClicked() {
        //TODO maby should be diffrent methods
        if(navController.getCurrentDestination().getLabel().equals("fragment_statistics")){
            navController.navigate(R.id.statisticsTabFragment);
        }
        else navController.navigate(R.id.reactionTestActivity);


    }

    @Override
    public void friendsDialogClicked() {
        friendsDialog.setContentView(R.layout.dialog_friends);


        friendsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        friendsDialog.show();
    }


    @Override
    public void chimpTestClicked() {
        navController.navigate(R.id.chimpGameActivity);
    }

    @Override
    public void visualGameClicked() {
        navController.navigate(R.id.visualGameFragment);
    }
}