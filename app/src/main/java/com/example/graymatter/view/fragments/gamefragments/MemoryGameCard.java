package com.example.graymatter.view.fragments.gamefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.graymatter.R;

/**
 * @author Viktor
 * the class that represents the fragment for each memoryGameCard
 */
public class MemoryGameCard extends Fragment {
    public TextView cardFrontMemory;
    public CardView cardBackMemory;

    /**
     * Initializes some general specifics for the cards
     * @return returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visual_game, container, false);
        cardFrontMemory = view.findViewById(R.id.card_front_memory);
        cardBackMemory = view.findViewById(R.id.card_back_memory);

        Float scale = getContext().getResources().getDisplayMetrics().density;
        cardFrontMemory.setCameraDistance(8000 * scale);
        cardBackMemory.setCameraDistance(8000 * scale);

        return view;
    }
}
