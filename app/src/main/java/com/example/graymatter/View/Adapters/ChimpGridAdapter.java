package com.example.graymatter.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graymatter.R;


public class ChimpGridAdapter extends BaseAdapter {
    private int[] grid;
    private boolean visibility = true;

    public ChimpGridAdapter(int[] grid) {
        this.grid = grid;
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
        TextView numberText = view.findViewById(R.id.cardNumber);
        ImageView imageView = view.findViewById(R.id.whiteBackgroud);
        if (grid[position] != 0){
            numberText.setText(String.valueOf(grid[position]));
            if (!visibility)
                numberText.setVisibility(View.INVISIBLE);
        }
        else{
            numberText.setText("");
            imageView.setImageResource(R.mipmap.ic_black_square_foreground);
        }
        return view;
    }

}
