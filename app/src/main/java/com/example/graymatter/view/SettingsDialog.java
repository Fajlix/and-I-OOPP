package com.example.graymatter.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.graymatter.R;
import com.example.graymatter.viewModel.ProfileViewModel;

import java.io.IOException;

public class SettingsDialog extends Dialog {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private ProfileViewModel profileViewModel;

    private FragmentChangeListener listener;

    public SettingsDialog(@NonNull Context context) {
        super(context);
        listener = (FragmentChangeListener)context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileViewModel = new ProfileViewModel();
        profileViewModel.init(listener.getDataAccess());


        //Creates an alert dialog that is shown when logoutButton is pressen, with a yes and a no button
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure you want to logout?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                listener.logoutClicked();
                try {
                    profileViewModel.logoutUser();  //TODO make it do something
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                listener.changeEmailClicked();
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.changePasswordClicked();
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
