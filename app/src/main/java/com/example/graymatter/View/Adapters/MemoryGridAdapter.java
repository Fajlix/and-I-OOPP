package com.example.graymatter.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.R;

import java.util.ArrayList;

public class MemoryGridAdapter extends BaseAdapter {
    private ArrayList<MemoryGrid.TileState> grid;
    private boolean visibility = true;

    public MemoryGridAdapter(ArrayList<MemoryGrid.TileState> grid) {
        this.grid = grid;
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

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_game_card, null);
        ImageView imageView = view.findViewById(R.id.whiteBackgroud);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (grid.get(position).equals(MemoryGrid.TileState.CORRECTHIDDEN)) {
            if (visibility) {
                imageView.setImageResource(R.mipmap.ic_white_memory_foreground);
            }
            else
                imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);

        }
        else if (grid.get(position).equals(MemoryGrid.TileState.CORRECTCHOSEN)) {
            imageView.setImageResource(R.mipmap.ic_green_memory_foreground);
        }
        else if (grid.get(position).equals(MemoryGrid.TileState.INCORRECTCHOSEN)) {
            imageView.setImageResource(R.mipmap.ic_red_memory_foreground);
        }
        else if (grid.get(position).equals(MemoryGrid.TileState.INCORRECTHIDDEN)) {
            imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
        }
        return view;
    }
}
