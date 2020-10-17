package com.example.graymatter.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.graymatter.R;

public class ToHHome extends Fragment {

    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_reaction_test, container, false);
        super.onCreate(savedInstanceState);
    }

    public void game(View v) {
        Intent i = new Intent(this, ToHLevels.class);
        startActivity(i);
    }

    public void instruct(View v) {
        Intent i = new Intent(this, ToHInstruct.class);
        startActivity(i);
    }

    public void exit(View v) {
        getActivity().finish();
        System.exit(0);
    }
}