package com.example.graymatter.View.Adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.example.graymatter.R;
import java.util.ArrayList;

/**
 * @author Viktor
 * the class that represents the gridView for the chimpGame
 */
public class ChimpGridAdapter extends GeneralAdapter {
    GameFragment gameFragment;

    AnimatorSet frontAnim;
    AnimatorSet backAnim;
    boolean isFront = true;

    private int lastPos;

    public ChimpGridAdapter(GameFragment gameFragment, ArrayList<Integer> grid, int lastPos) {
        setGrid(grid);
        this.gameFragment = gameFragment;
        this.lastPos = lastPos;
    }

    /**
     * When a tile has been clicked this method communicate with the fragment
     * @param position is which tile that has been clicked
     */
    public void makeMove(int position) {
        gameFragment.makeMove(position);
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

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(gameFragment.getContext(),R.animator.front_animation);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(gameFragment.getContext(),R.animator.back_animation);

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
                        isFront = false;
                    lastPos = position;
                    makeMove(position);
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
