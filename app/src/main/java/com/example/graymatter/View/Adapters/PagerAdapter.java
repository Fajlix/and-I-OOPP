package com.example.graymatter.View.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    StatisticsFragmentInterface SFI;

    private String[] namesFriends, scoresFriends, namesGlobal, scoresGlobal;
    private int[] picturesFriends, picturesGlobal;

    String game;

    public PagerAdapter(FragmentManager fm, int numOfTabs, String[] namesFriends,
                        String[] scoresFriends, int[] picturesFriends, String[] namesGlobal,
                        String[] scoresGlobal, int[] picturesGlobal, String game, StatisticsFragmentInterface SFI) {
        super(fm);
        this.SFI = SFI;
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
                return SFI.getFragment(position,namesFriends, scoresFriends, picturesFriends, game);
            case 1:
                return SFI.getFragment(position,namesGlobal, scoresGlobal, picturesGlobal, game);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
