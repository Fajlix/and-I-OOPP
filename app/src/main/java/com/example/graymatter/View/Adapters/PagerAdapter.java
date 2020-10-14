package com.example.graymatter.View.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graymatter.View.Fragments.StatisticsFriendsFragment;
import com.example.graymatter.View.Fragments.StatisticsGlobalFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        
        this.numOfTabs = numOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new StatisticsFriendsFragment();
            case 1:
                return new StatisticsGlobalFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
