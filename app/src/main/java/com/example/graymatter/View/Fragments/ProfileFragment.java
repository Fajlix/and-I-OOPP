package com.example.graymatter.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.graymatter.R;


public class ProfileFragment extends Fragment {

    private FragmentChangeListener listener;

    public ProfileFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        listener = (FragmentChangeListener)getContext();

        Button friendsButton = (Button)view.findViewById(R.id.btnFriends);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.friendsDialogClicked();
            }
        });

        return view;
    }
}