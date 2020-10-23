package com.example.graymatter.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;
import com.example.graymatter.View.Adapters.LeaderboardAdapter;


public class StatisticsFriendsFragment extends Fragment{

    private String gameName;
    private String[] names;
    private int[] pictures, scores;

    public StatisticsFriendsFragment(String[] names, int[] scores, int[] pictures, String game) {
        this.names = names;
        this.scores = scores;
        this.pictures = pictures;
        gameName = game;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_friends, container, false);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TextView textViewGame = (TextView) view.findViewById(R.id.textViewGame);


        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(getContext(), names, pictures, scores);
        recyclerView.setAdapter(leaderboardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        textViewGame.setText(gameName + " Leaderboard");

        return view;
    }


}