package com.example.graymatter.View.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.graymatter.R;
import com.example.graymatter.View.LeaderboardAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class StatisticsGlobalFragment extends Fragment {

    private String gameName;
    private TextView textViewGame;

    public StatisticsGlobalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_global, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        textViewGame = (TextView)view.findViewById(R.id.textViewGame);


        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(getContext(), new String[]{"Aj", "sak", "dak"}, new int[]{2,3,4},new String[]{"12", "123", "1244"});
        recyclerView.setAdapter(leaderboardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void update(String gameName){

    }

}