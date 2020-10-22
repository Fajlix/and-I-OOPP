package com.example.graymatter.View.Adapters;

import android.content.Context;
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

/**
 * @author Viktor
 * class that represents a general adapter for a grid view
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    String[] nameArray;
    String[] descArray;
    String[] colors;
    int[] logos;
    Context context;
    FragmentChangeListener fragmentChangeListener;

    public RecyclerViewAdapter(Context c, String[] gameName, String[] gameDescription, String[] colors, int[] logos) {
        context = c;
        nameArray = gameName;
        descArray = gameDescription;
        this.logos = logos;
        this.colors = colors;
    }

    /**
     * Initializes the ViewHolders, an object that hold the view and represents it
     * @return it returns the viewholder
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_page_button, parent, false);
        fragmentChangeListener = (FragmentChangeListener) parent.getContext();
        return new RecyclerViewHolder(view);
    }

    /**
     * This method is called for each ViewHolder to bind it to the adapter
     * @param holder represents the specific viewHolder
     * @param position represents the position of the view
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.title.setText(nameArray[position]);
        holder.description.setText(descArray[position]);
        holder.logo.setImageResource(logos[position]);
        holder.mainCard.setBackgroundColor(Color.parseColor(colors[position]));

        holder.mainCard.setOnClickListener(new View.OnClickListener() {
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
                    fragmentChangeListener.ToHClicked();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logos.length;
    }


    /**
     * a class that navigate to the necessary components that are being modified
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView logo;
        LinearLayout mainCard;

        public RecyclerViewHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gamesTxt);
            description = itemView.findViewById(R.id.gamesDescTxt);
            logo = itemView.findViewById(R.id.gameLogo);
            mainCard = itemView.findViewById(R.id.mainScreenCard);
        }
    }
}