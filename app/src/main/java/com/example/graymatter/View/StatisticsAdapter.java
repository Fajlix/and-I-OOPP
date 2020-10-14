package com.example.graymatter.View;

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

//TODO This is basically a copy of RecyclerViewAdapter, so should prob do sum smart abstractions instead
public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {
    String[] nameArray;
    String[] descArray;
    String[] colors;
    int[] logos;
    Context context;
    FragmentChangeListener fragmentChangeListener;

    public StatisticsAdapter (Context c, String[] gameName, String[] gameDescription, String[] colors, int[] logos) {
        context = c;
        nameArray = gameName;
        descArray = gameDescription;
        this.logos = logos;
        this.colors = colors;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.statistics_game_card, parent, false);
        fragmentChangeListener = (FragmentChangeListener) parent.getContext();
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, final int position) {
        holder.title.setText(nameArray[position]);
        holder.description.setText(descArray[position]);
        holder.logo.setImageResource(logos[position]);
        holder.mainCard.setBackgroundColor(Color.parseColor(colors[position]));

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
                    //fragmentChangeListener.towerOfHanoiClicked();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logos.length;
    }

    public class StatisticsViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView logo;
        LinearLayout mainCard;
        ConstraintLayout mainLayout;

        public StatisticsViewHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.statisticsGamesTxt);
            description = itemView.findViewById(R.id.statisticsGamesDescTxt);
            logo = itemView.findViewById(R.id.statisticsGameLogo);
            mainCard = itemView.findViewById(R.id.statisticsMainCard);
            mainLayout = itemView.findViewById(R.id.statisticsMainLayout);
        }
    }


}