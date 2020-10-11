package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.graymatter.R;
import com.example.graymatter.ViewModel.ChimpGameViewModel;
import com.example.graymatter.ViewModel.TowerOfHanoiViewModel;

public class TowerOfHanoiFragment extends Fragment {
    private TowerOfHanoiViewModel towerOfHanoiVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tower_of_hanoi, container, false);
        super.onCreate(savedInstanceState);

        return view;
    }

    public void tileClicked(int n){
        towerOfHanoiVM.tileHasBeenClicked(n);
    }
}
