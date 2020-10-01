package com.example.graymatter.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.graymatter.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.R;
import com.example.graymatter.View.Fragments.GameFragments.VisualGameFragment;

import java.util.ArrayList;

public class MemoryGridAdapter extends BaseAdapter {
    private ArrayList<MemoryGrid.TileState> grid;
    VisualGameFragment context;
    private MemoryGame memoryGame;

    public MemoryGridAdapter(VisualGameFragment context, ArrayList<MemoryGrid.TileState> grid) {
        this.grid = grid;
        this.context = context;
    }

    public void tileHasBeenClicked(View v){
        context.tileClicked(v);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        memoryGame = new MemoryGame();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_game_card, null);
        ImageView imageView = view.findViewById(R.id.whiteBackgroud);

        if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.CORRECTHIDDEN)) {
            imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
        }
        else if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.CORRECTCHOSEN)) {
            imageView.setImageResource(R.mipmap.ic_green_memory_foreground);
        }
        else if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.INCORRECTCHOSEN)) {
            imageView.setImageResource(R.mipmap.ic_red_memory_foreground);
        }
        else if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.INCORRECTHIDDEN)) {
            imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
        }

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tileHasBeenClicked(v);
            }
        });

        return view;
    }

}
