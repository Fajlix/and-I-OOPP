package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.graymatter.View.MainScreenGridAdapter;
import com.example.graymatter.R;


public class GamesFragment extends Fragment {

    //private BottomNavigationView bottomNavigationView;




    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        final GamesFragment GF = this;

        //bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView);

        GridView mainScreenGrid = (GridView) view.findViewById(R.id.mainScreenGrid);
        mainScreenGrid.bringToFront();
        mainScreenGrid.setAdapter(new MainScreenGridAdapter(getActivity()));
        mainScreenGrid.setNumColumns(2);


        return view;
    }



}