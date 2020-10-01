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

public class MemoryGridAdapter extends BaseAdapter {
    private int[] grid;
    private boolean visibility = true;
    VisualGameFragment context;
    private MemoryGame memoryGame;

    public MemoryGridAdapter(VisualGameFragment context, int[] grid) {
        this.grid = grid;
        this.context = context;
    }

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    public void tileHasBeenClicked(View v){
        context.tileClicked(Integer.parseInt((String) textView.getText()));
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
        memoryGame = new MemoryGame();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visual_game_card, null);
        ImageView imageView = view.findViewById(R.id.whiteBackgroud);

        if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.CORRECTHIDDEN)) {
            imageView.setImageResource(R.mipmap.ic_white_square_foreground);
            if (!visibility)
                imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
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
