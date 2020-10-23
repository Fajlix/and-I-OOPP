package com.example.graymatter.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graymatter.R;
import com.example.graymatter.view.adapters.PagerAdapter;
import com.example.graymatter.view.FragmentChangeListener;
import com.example.graymatter.viewModel.StatisticsViewModel;
import com.google.android.material.tabs.TabLayout;


public class StatisticsTabFragment extends Fragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private StatisticsViewModel statisticsViewModel;
    private FragmentChangeListener listener;

    private String game;


    public StatisticsTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_tab, container, false);
        listener = (FragmentChangeListener)getContext();

        game = listener.getGame();

        statisticsViewModel = new StatisticsViewModel();
        statisticsViewModel.init(listener.getDataAccess(), game);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);


        //Configures tabLayout navigation
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(),
                statisticsViewModel.getTopFriendsUsernames(), statisticsViewModel.getTopFriendsUserScores(),
                statisticsViewModel.getTopFriendsUserImages(), statisticsViewModel.getTopGlobalUsernames(), statisticsViewModel.getTopGlobalUserScores(),
                statisticsViewModel.getTopGlobalUserImages(), game,new StatisticsFactory());
        viewPager.setAdapter(pagerAdapter);


        //tabLayout listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        return view;
    }

    public void setGame(String game){
        this.game = game;

    }


}