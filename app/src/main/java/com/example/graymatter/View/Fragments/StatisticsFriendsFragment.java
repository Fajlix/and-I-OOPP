package com.example.graymatter.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.graymatter.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class StatisticsFriendsFragment extends Fragment {


    public StatisticsFriendsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_friends, container, false);


        return view;
    }
}