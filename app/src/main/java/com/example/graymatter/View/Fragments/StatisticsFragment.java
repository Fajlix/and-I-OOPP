package com.example.graymatter.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graymatter.R;
import com.example.graymatter.View.StatisticsAdapter;

//TODO almost the same as GamesFragment
public class StatisticsFragment extends Fragment{

    private String[] gameName;
    private String[] colors;
    private int[] logos = {R.mipmap.ic_reaction_test_logo_foreground,
            R.mipmap.ic_chimp_game_logo_foreground,
            R.mipmap.ic_visual_memory_game_logo_foreground,
            R.mipmap.ic_tower_of_hanoi_logo_foreground};
    RecyclerView recyclerView;


    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);


        recyclerView = view.findViewById(R.id.statisticsRecyclerView);

        gameName = getResources().getStringArray(R.array.games);
        colors = getResources().getStringArray(R.array.gameCardColors);


        StatisticsAdapter statisticsAdapter = new StatisticsAdapter(inflater.getContext(), gameName, colors, logos);
        recyclerView.setAdapter(statisticsAdapter);
        recyclerView.bringToFront();
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));


        return view;
    }



}