package com.example.graymatter.view.fragments;

import androidx.fragment.app.Fragment;

import com.example.graymatter.view.adapters.StatisticsFragmentInterface;

public class StatisticsFactory implements StatisticsFragmentInterface {


    public Fragment getFragment(int pos,String[] names, int[] scores, int[] pictures, String game) {
        switch (pos) {
            case 0:
                return new StatisticsFriendsFragment(names, scores, pictures, game);
            case 1:
                return new StatisticsGlobalFragment(names, scores, pictures, game);
            default:
                return null;
        }
    }
}
