package com.example.graymatter.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.graymatter.View.Adapters.RecyclerViewAdapter;
import com.example.graymatter.View.MainScreenGridAdapter;
import com.example.graymatter.R;


public class GamesFragment extends Fragment {

    String gameName[], gameDescription[], colors[];
    int logos[] = {R.mipmap.ic_reaction_test_logo_foreground,
            R.mipmap.ic_chimp_game_logo_foreground,
            R.mipmap.ic_visual_memory_game_logo_foreground,
            R.mipmap.ic_tower_of_hanoi_logo_foreground};
    RecyclerView recyclerView;

    //private BottomNavigationView bottomNavigationView;




    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        //bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView);

        recyclerView = view.findViewById(R.id.recycleView);

        gameName = getResources().getStringArray(R.array.games);
        gameDescription = getResources().getStringArray(R.array.gameDescription);
        colors = getResources().getStringArray(R.array.gameCardColors);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(inflater.getContext(), gameName, gameDescription, colors, logos);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.bringToFront();
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));



        return view;
    }



}