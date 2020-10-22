package com.example.graymatter.View.Adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.R;
import com.example.graymatter.View.Fragments.GameFragments.MemoryGameFragment;

import java.util.ArrayList;

/**
 * @author Viktor
 * class that represents memory game adapter
 */
public class MemoryGridAdapter extends GeneralAdapter {
    MemoryGameFragment context;

    AnimatorSet frontAnim;
    AnimatorSet backAnim;
    boolean isFront = true;
    private int lastPos;

    public MemoryGridAdapter(MemoryGameFragment context, ArrayList<MemoryGrid.TileState> grid, int lastPos) {
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
     * tile has been clicked, and depending on if it is the correct one or not it modifies the view
     * @param position represents which tile it is
     * @return returns the view for the tile
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_game_card, null);
        CardView cardBackMemory = view.findViewById(R.id.card_back_memory);
        CardView memoryCardView = view.findViewById(R.id.memoryCardView);
        memoryCardView.setCardBackgroundColor(0xFF8A8A8A);

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context.getContext(),R.animator.front_animation);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context.getContext(),R.animator.back_animation);

        if (grid.get(position).equals(MemoryGrid.TileState.CORRECTHIDDEN)) {
            if (visibility) {
                memoryCardView.setCardBackgroundColor(0xFFFFFFFF);
            }
            else
            {
                memoryCardView.setCardBackgroundColor(0xFF8A8A8A);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFront) {
                        isFront = false;
                    }

                    tileHasBeenClicked(position);
                }
            });
        }
        else if (grid.get(position).equals(MemoryGrid.TileState.INCORRECTHIDDEN)) {
            memoryCardView.setCardBackgroundColor(0xFF8A8A8A);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFront) {
                        isFront = false;
                    }

                    tileHasBeenClicked(position);
                }
            });
        }
        else if (grid.get(position).equals(MemoryGrid.TileState.CORRECTCHOSEN)) {
            cardBackMemory.setCardBackgroundColor(0xFF4CAF50);

            if (lastPos == position)
            {
                frontAnim.setTarget(memoryCardView);
                backAnim.setTarget(cardBackMemory);
                frontAnim.start();
                backAnim.start();
            }
            else
            {
                memoryCardView.setCardBackgroundColor(0xFF4CAF50);
            }
        }
        else if (grid.get(position).equals(MemoryGrid.TileState.INCORRECTCHOSEN)) {
            cardBackMemory.setCardBackgroundColor(0xFFFF856A);

            if (lastPos == position)
            {
                frontAnim.setTarget(memoryCardView);
                backAnim.setTarget(cardBackMemory);
                frontAnim.start();
                backAnim.start();
            }
            else
            {
                memoryCardView.setCardBackgroundColor(0xFFFF856A);
            }
        }
        return view;
    }
}
