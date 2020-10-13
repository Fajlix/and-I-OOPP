package com.example.graymatter.View.Adapters;

import android.animation.AnimatorSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graymatter.R;
import com.example.graymatter.View.Fragments.GameFragments.ChimpGameFragment;


public class ChimpGridAdapter extends BaseAdapter {
    private int[] grid;
    private boolean visibility = true;
    ChimpGameFragment context;

    public ChimpGridAdapter(ChimpGameFragment context, int[] grid) {
        this.grid = grid;
        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chimp_game_card, null);
        TextView cardFront = view.findViewById(R.id.card_front);
        if (grid[position] != 0){
            cardFront.setBackgroundColor(0xFFFFFFFF);
            cardFront.setText(String.valueOf(grid[position]));
            if (!visibility)
                cardFront.setTextColor(0xFFFFFFFF);
        }
        else{
            cardFront.setText("");
            cardFront.setBackgroundColor(0x00000000);
        }
        return view;
    }
}
