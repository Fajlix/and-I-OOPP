package com.example.graymatter.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.graymatter.R;
import com.example.graymatter.ReactionTime;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ReactionTime reactionTime;
    private boolean firstClick = true;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final Button reactButton = root.findViewById(R.id.react_button);
        reactionTime = new ReactionTime();
        reactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("pressed");
                if (firstClick) {
                    firstClick = false;
                    reactionTime.StartTest();
                }
                else{
                    reactionTime.StopTest();
                    firstClick = true;
                }
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}