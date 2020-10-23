package com.example.graymatter.view.fragments.gamefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.graymatter.R;

/**
 * @author Viktor Felix
 * the class that represents the fragment for each chimpGameCard
 */
public class ChimpGameCard extends Fragment {
    public TextView cardFront;
    public TextView cardBack;
    int number;

    /**
     * Initializes some general specifics for the cards
     * @return returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chimp_game, container, false);
        cardFront = view.findViewById(R.id.card_front_chimp);
        cardBack = view.findViewById(R.id.card_back_chimp);
        cardFront.setText(String.valueOf(number));

        Float scale = getContext().getResources().getDisplayMetrics().density;
        cardFront.setCameraDistance(8000 * scale);
        cardBack.setCameraDistance(8000 * scale);

        return view;
    }
}
