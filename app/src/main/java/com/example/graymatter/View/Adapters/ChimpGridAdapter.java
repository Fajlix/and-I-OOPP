package com.example.graymatter.View.Adapters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.graymatter.R;
import com.example.graymatter.View.Fragments.GameFragments.ChimpGameFragment;

import java.util.ArrayList;

/**
 * @author Viktor
 * the class that represents the gridView for the chimpGame
 */
public class ChimpGridAdapter extends GeneralAdapter {
    ChimpGameFragment context;

    AnimatorSet frontAnim;
    AnimatorSet backAnim;
    boolean isFront = true;

    private int lastPos;

    public ChimpGridAdapter(ChimpGameFragment context, ArrayList<Integer> grid, int lastPos) {
        setGrid(grid);
        this.context = context;
        this.lastPos = lastPos;
    }

    /**
     * When a tile has been clicked this method communicate with the fragment
     * @param position is which tile that has been clicked
     */
    public void tileHasBeenClicked(int position) {
        context.tileHasBeenClicked(position);
    }

    /**
     * This method creates the view for each grid tile and also creates the animation for when the
     * tile has been clicked
     * @param position represents which tile it is
     * @return returns the view for the tile
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chimp_game_card, null);
        final TextView cardFrontChimp = view.findViewById(R.id.card_front_chimp);
        final TextView cardBackChimp = view.findViewById(R.id.card_back_chimp);
        CardView chimpCardView = view.findViewById(R.id.chimpCardView);

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context.getContext(),R.animator.front_animation);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context.getContext(),R.animator.back_animation);

        if (grid.get(position).equals(0)){

            if (lastPos == position)
            {
                cardFrontChimp.setText("");
                chimpCardView.setCardBackgroundColor(0xFFFFFFFF);
                frontAnim.setTarget(chimpCardView);
                backAnim.setTarget(cardBackChimp);
                frontAnim.start();
                backAnim.start();
            }
            else
            {
                cardFrontChimp.setText("");
                chimpCardView.setBackgroundColor(0x00);
            }
        }
        else
        {
            chimpCardView.setCardBackgroundColor(0xFFFFFFFF);
            cardFrontChimp.setText(String.valueOf(grid.get(position)));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFront)
                    {
                        isFront = false;
                    }

                    lastPos = position;

                    tileHasBeenClicked(position);
                }
            });

            if (!visibility)
            {
                cardFrontChimp.setTextColor(0xFFFFFFFF);
            }
        }
        return view;
    }
}
