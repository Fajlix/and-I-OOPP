package com.example.graymatter.View;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;

public class FriendsDialog extends Dialog {

    RecyclerView recyclerView;

    String[] names;
    int[] images = {1,2,3};

    public FriendsDialog(@NonNull Context context, String[] names) {
        super(context);
        this.names = names;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_friends);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.friendsRecyclerView);
        FriendsAdapter friendsAdapter = new FriendsAdapter(super.getContext(), names, images);
        recyclerView.setAdapter(friendsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));

    }


    public void addFriends(){


    }
}
