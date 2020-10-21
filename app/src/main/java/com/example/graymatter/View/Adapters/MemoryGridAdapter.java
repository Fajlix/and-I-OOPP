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

public class MemoryGridAdapter extends BaseAdapter {
    private ArrayList<MemoryGrid.TileState> grid;
    private boolean visibility = true;
    MemoryGameFragment context;

    AnimatorSet frontAnim;
    AnimatorSet backAnim;
    boolean isFront = true;
    private int lastPos;

    public MemoryGridAdapter(MemoryGameFragment context, ArrayList<MemoryGrid.TileState> grid, int lastPos) {
        this.grid = grid;
        this.context = context;
        this.lastPos = lastPos;
    }
    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    // how many tiles on the board
    @Override
    public int getCount() {
        return grid.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void tileHasBeenClicked(int position) {
        context.tileHasBeenClicked(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_game_card, null);
        final TextView cardFrontMemory = view.findViewById(R.id.card_front_memory);
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
