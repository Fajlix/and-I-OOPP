package com.example.graymatter.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;
import com.example.graymatter.View.FragmentChangeListener;
import com.example.graymatter.View.Fragments.GameFragments.ChimpGameFragment;
import com.example.graymatter.View.Fragments.GameFragments.TowerOfHanoiFragment;
import com.example.graymatter.View.Fragments.GameFragments.VisualGameFragment;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    String nameArray[], descArray[], colors[];
    int logos[];
    Context context;
    FragmentChangeListener fragmentChangeListener;

    public RecyclerViewAdapter (Context c, String gameName[], String gameDescription[], String colors[], int logos[]) {
        context = c;
        nameArray = gameName;
        descArray = gameDescription;
        this.logos = logos;
        this.colors = colors;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_screen_cards, parent, false);
        fragmentChangeListener = (FragmentChangeListener) parent.getContext();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.title.setText(nameArray[position]);
        holder.description.setText(descArray[position]);
        holder.logo.setImageResource(logos[position]);
        holder.mainCard.setBackgroundColor(Color.parseColor(colors[position]));
        // holder.mainCard.setElevation(20);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (position == 0)
                {
                    fragmentChangeListener.reactionTestClicked();
                }
                else if (position == 1)
                {
                    fragmentChangeListener.chimpTestClicked();
                }
                else if (position == 2)
                {
                    fragmentChangeListener.visualGameClicked();
                }
                else if (position == 3)
                {
                    fragmentChangeListener.towerOfHanoiClicked();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logos.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView logo;
        LinearLayout mainCard;
        ConstraintLayout mainLayout;

        public RecyclerViewHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gamesTxt);
            description = itemView.findViewById(R.id.gamesDescTxt);
            logo = itemView.findViewById(R.id.gameLogo);
            mainCard = itemView.findViewById(R.id.mainCard);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }


}