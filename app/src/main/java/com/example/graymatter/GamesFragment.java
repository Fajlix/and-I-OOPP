package com.example.graymatter;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class GamesFragment extends Fragment {

    //private BottomNavigationView bottomNavigationView;

    private FragmentChangeListener listener;



    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        listener = (FragmentChangeListener)getContext();

        final GamesFragment GF = this;

        //bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView);

        GridView mainScreenGrid = (GridView) view.findViewById(R.id.mainScreenGrid);
        mainScreenGrid.bringToFront();
        mainScreenGrid.setAdapter(new MainScreenGridAdapter(getActivity()));
        mainScreenGrid.setNumColumns(2);


        return view;
    }



}