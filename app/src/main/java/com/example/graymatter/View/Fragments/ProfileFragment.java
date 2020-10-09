package com.example.graymatter.View.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.graymatter.R;
import com.example.graymatter.View.FragmentChangeListener;
import com.example.graymatter.View.FriendsDialog;
import com.example.graymatter.ViewModel.ProfileViewModel;


public class ProfileFragment extends Fragment {

    private FragmentChangeListener listener;

    TextView profileName, username, email, scoe;


    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        listener = (FragmentChangeListener)getContext();

         profileName = (TextView)view.findViewById(R.id.textViewProfileName);


        final ProfileViewModel profileViewModel = new ProfileViewModel();
        profileViewModel.init();



        Button friendsButton = (Button)view.findViewById(R.id.btnFriends);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.friendsDialogClicked();

                Dialog friendsDialog = new FriendsDialog(getContext(), profileViewModel.getFriends());

                //friendsDialog.setContentView(R.layout.dialog_friends);

                friendsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                friendsDialog.show();
            }
        });
        Button settingsButton = (Button)view.findViewById(R.id.btnSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.settingsDialogClicked();
            }
        });


        return view;
    }

    public void setProfileName(TextView profileName) {
        this.profileName = profileName;
    }

}