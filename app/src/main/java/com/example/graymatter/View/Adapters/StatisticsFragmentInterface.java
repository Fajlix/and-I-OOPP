package com.example.graymatter.View.Adapters;

import androidx.fragment.app.Fragment;

public interface StatisticsFragmentInterface {
    Fragment getFragment(int pos,String[] names, String[] scores, int[] pictures, String game);
}
