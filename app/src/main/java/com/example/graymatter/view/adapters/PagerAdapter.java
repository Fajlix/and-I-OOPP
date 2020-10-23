package com.example.graymatter.view.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    StatisticsFragmentInterface sFI;

    private String[] namesFriends, namesGlobal;
    private int[] picturesFriends, picturesGlobal, scoresFriends, scoresGlobal;

    String game;

    public PagerAdapter(FragmentManager fm, int numOfTabs, String[] namesFriends,
                        int[] scoresFriends, int[] picturesFriends, String[] namesGlobal,
                        int[] scoresGlobal, int[] picturesGlobal, String game, StatisticsFragmentInterface sFI){
        super(fm);
        this.sFI = sFI;
        this.numOfTabs = numOfTabs;

        this.namesFriends = namesFriends;
        this.scoresFriends = scoresFriends;
        this.picturesFriends = picturesFriends;

        this.namesGlobal = namesGlobal;
        this.scoresGlobal = scoresGlobal;
        this.picturesGlobal = picturesGlobal;

        this.game = game;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return sFI.getFragment(position,namesFriends, scoresFriends, picturesFriends, game);
            case 1:
                return sFI.getFragment(position,namesGlobal, scoresGlobal, picturesGlobal, game);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
