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
import com.example.graymatter.View.StatisticsChangeListener;

//TODO almost the same as GamesFragment
public class StatisticsFragment extends Fragment implements StatisticsChangeListener {

    String[] gameName;
    String[] gameDescription;
    String[] colors;
    int[] logos = {R.mipmap.ic_reaction_test_logo_foreground,
            R.mipmap.ic_chimp_test_logo_foreground,
            R.mipmap.ic_visual_memory_game_logo_foreground,};
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

//        gameName = getResources().getStringArray(R.array.games);
//        gameDescription = getResources().getStringArray(R.array.gameDescription);
//        colors = getResources().getStringArray(R.array.gameCardColors);

        gameName = new String[]{"Toer", "tes", "ss"};
        gameDescription = new String[]{"Toer", "tes", "ss"};
        colors = new String[]{"#636161", "#232323", "#999999"};


        StatisticsAdapter statisticsAdapter = new StatisticsAdapter(inflater.getContext(), gameName, gameDescription, colors, logos);
        recyclerView.setAdapter(statisticsAdapter);
        recyclerView.bringToFront();
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));


        return view;
    }


    @Override
    public void reactionTestClicked() {

    }

    @Override
    public void chimpTestClicked() {

    }

    @Override
    public void visualGameClicked() {

    }
}