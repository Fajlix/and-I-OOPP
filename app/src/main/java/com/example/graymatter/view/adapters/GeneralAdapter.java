package com.example.graymatter.view.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * @author Viktor
 * class that represents a general adapter for a grid view
 */
public abstract class GeneralAdapter extends BaseAdapter {
    protected ArrayList grid;
    protected boolean visibility = true;

    @Override
    public int getCount() {
        return grid.size();
    }

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public void setGrid(ArrayList grid) {
        this.grid = grid;
    }
}
