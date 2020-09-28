package com.example.graymatter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ChimpTestGridAdapter extends BaseAdapter {
    private Context context;
    private int[] array;

    public ChimpTestGridAdapter(Context context, int[] array)
    {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount ()
    {
        return 4;
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

        if (convertView == null)
        {
            imageView = new ImageView(context);
            //imageView.setPadding(15,5,8,8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(thumbsIds[array[0]]);

        if (array[position] > 0)
        {
            imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    imageView.setImageResource(thumbsIds[4]);
                }
            });
        }
        else if (array[position] == 0)
            imageView.setImageResource(thumbsIds[6]);

        return imageView;
    }

    // array of images
    public Integer[] thumbsIds = {
            R.mipmap.ic_black_square_foreground,
            R.mipmap.ic_1_foreground,
            R.mipmap.ic_2_foreground,
            R.mipmap.ic_3_foreground,
            R.mipmap.ic_4_foreground,
            R.mipmap.ic_5_foreground,
            R.mipmap.ic_6_foreground,
            R.mipmap.ic_7_foreground,
            R.mipmap.ic_8_foreground,
            R.mipmap.ic_9_foreground,
            R.mipmap.ic_10_foreground,
            R.mipmap.ic_11_foreground,
            R.mipmap.ic_12_foreground,
            R.mipmap.ic_13_foreground,
            R.mipmap.ic_14_foreground,
            R.mipmap.ic_15_foreground,
            R.mipmap.ic_16_foreground,
            R.mipmap.ic_17_foreground,
            R.mipmap.ic_18_foreground,
            R.mipmap.ic_19_foreground,
            R.mipmap.ic_20_foreground,
            R.mipmap.ic_white_square_foreground,
            R.mipmap.ic_red_x_foreground,
            R.mipmap.ic_checkmark_foreground
    };
}