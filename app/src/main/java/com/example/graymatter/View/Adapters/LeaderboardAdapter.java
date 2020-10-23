package com.example.graymatter.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {
    //TODO Adapter inheritance should be implemented

    private String[] names;
    private int[] images, score;
    private Context context;
    private int placement = 1;

    public LeaderboardAdapter(Context context, String[] names, int[] images ,int[] score){
        this.names = names;
        this.images = images;
        this.score = score;
        this.context = context;
    }


    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_leaderboard, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        holder.nameTextView.setText(names[position]);
        //holder.imageView.setImageResource(images[position]);
        holder.scoreTextView.setText(String.valueOf(score[position]) + " perc.");
        holder.placementTextView.setText(String.valueOf(placement));

        placement++;
    }

    @Override
    public int getItemCount() {
        return names.length;
    }




    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, scoreTextView, placementTextView;
        ImageView imageView;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friendsNameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            placementTextView = itemView.findViewById(R.id.placementTextView);
            imageView = itemView.findViewById(R.id.friendsImageView);

        }
    }
}
