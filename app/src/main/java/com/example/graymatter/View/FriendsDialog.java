package com.example.graymatter.View;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;

public class FriendsDialog extends Dialog {

    RecyclerView recyclerView;

    String[] names;

    public FriendsDialog(@NonNull Context context, String[] names, RecyclerView recyclerView) {
        super(context);
        this.names = names;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_friends);

    }


    public void addFriends(){


    }
}
