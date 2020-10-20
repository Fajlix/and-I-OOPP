package com.example.graymatter.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.R;
import com.example.graymatter.ViewModel.MemoryGameViewModel;
import com.example.graymatter.ViewModel.ProfileViewModel;

public class SettingsDialog extends Dialog {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private ProfileViewModel profileViewModel;

    FragmentChangeListener listener;

    public SettingsDialog(@NonNull Context context) {
        super(context);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO is this how you do it?
        profileViewModel = new ProfileViewModel();

        listener = (FragmentChangeListener) getContext();

        //Creates an alert dialog that is shown when logoutButton is pressen, with a yes and a no button
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure you want to logout?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        alertDialog = builder.create();





        Button changeEmailButton = (Button)findViewById(R.id.changeEmailButton);
        Button changePasswordButton = (Button)findViewById(R.id.changePasswordButton);
        Button logoutButton = (Button)findViewById(R.id.logoutButton);


        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });



    }
}
