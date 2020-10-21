package com.example.graymatter.View.Adapters;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.R;
import com.example.graymatter.View.Fragments.GameFragments.ChimpGameCard;
import com.example.graymatter.View.Fragments.GameFragments.ChimpGameFragment;
import com.example.graymatter.ViewModel.ChimpGameViewModel;


public class ChimpGridAdapter extends BaseAdapter {
    private int[] grid;
    private boolean visibility = true;
    ChimpGameFragment context;

    AnimatorSet frontAnim;
    AnimatorSet backAnim;
    boolean isFront = true;

    private int lastPos;

    public ChimpGridAdapter(ChimpGameFragment context, int[] grid, int lastPos) {
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
        return grid.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chimp_game_card, null);
        final TextView cardFrontChimp = view.findViewById(R.id.card_front_chimp);
        final TextView cardBackChimp = view.findViewById(R.id.card_back_chimp);
        CardView chimpCardView = view.findViewById(R.id.chimpCardView);

        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context.getContext(),R.animator.front_animation);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(context.getContext(),R.animator.back_animation);

        if (grid[position] != 0){
            chimpCardView.setCardBackgroundColor(0xFFFFFFFF);
            cardFrontChimp.setText(String.valueOf(grid[position]));

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
        else
        {
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
        return view;
    }
}
