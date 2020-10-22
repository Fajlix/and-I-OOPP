package com.example.graymatter.View.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * @author Viktor
 * class that represents a general adapter for a grid view
 */
public class GeneralAdapter extends BaseAdapter {
    ArrayList grid;
    boolean visibility = true;

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

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setGrid(ArrayList grid) {
        this.grid = grid;
    }
}
