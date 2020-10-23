package com.example.graymatter.view.adapters;

import androidx.fragment.app.Fragment;

public interface StatisticsFragmentInterface {
    Fragment getFragment(int pos,String[] names, int[] scores, int[] pictures, String game);
}
