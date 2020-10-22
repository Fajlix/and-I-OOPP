package com.example.graymatter.View;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.Model.dataAccess.social.UserInfoException;
import com.example.graymatter.R;
import com.example.graymatter.ViewModel.ProfileViewModel;

public class FriendsDialog extends Dialog {

    private FragmentChangeListener listener;

    private String[] names;
    private int[] images = {1,2,3}; //TODO implement real images

    public FriendsDialog(@NonNull Context context) {
        super(context);
        listener = (FragmentChangeListener)context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_friends);

        ProfileViewModel profileViewModel = new ProfileViewModel();
        profileViewModel.init(listener.getDataAccess());

        try {
            names = profileViewModel.getFriends();
        } catch (UserInfoException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.friendsRecyclerView);
        FriendsAdapter friendsAdapter = new FriendsAdapter(super.getContext(), names, images);
        recyclerView.setAdapter(friendsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));

    }


    public void addFriends(){


    }
}
