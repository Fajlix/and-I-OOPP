package com.example.graymatter.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.graymatter.R;
import com.example.graymatter.View.FragmentChangeListener;

public class MainScreenGridAdapter extends BaseAdapter {
    private Context context;
    private FragmentChangeListener listener;

    public MainScreenGridAdapter(Context context) {
        this.context = context;
    }

    // how many tiles on the board
    @Override
    public int getCount() {
        return 24;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // initializes the view of each grid
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            // imageView.setPadding(15,5,8,8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(thumbsIds[position]);

        listener = (FragmentChangeListener) parent.getContext();

        if (position == 0)
        {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.reactionTestClicked();
                }
            });
        }
        if (position == 1)
        {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.chimpTestClicked();
                }
            });
        }
        if (position == 2)
        {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.visualGameClicked();
                }
            });
        }
        return imageView;
    }

    // array of images
    public Integer[] thumbsIds = {
            R.mipmap.ic_reaction_test_logo_foreground,
            R.mipmap.ic_chimp_game_logo_foreground,
            R.mipmap.ic_visual_memory_game_logo_foreground
    };
}