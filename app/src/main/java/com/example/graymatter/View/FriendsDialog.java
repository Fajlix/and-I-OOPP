package com.example.graymatter.View;

import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;

public class FriendsDialog extends Dialog {

    RecyclerView recyclerView;

    String[] names = {"Stefan", "Olof", "Vin"};
    int[] images = {1,2,3};

    public FriendsDialog(@NonNull Context context) {
        super(context);

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
